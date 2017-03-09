package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;


/**
 * Created by Travis Holloway on 2/17/2017.
 * A system to control movement
 */

public class MovementSystem extends SubSystem {
    private int counter;
    private float myShrinkAmount;

    public MovementSystem(GameView theGameView) {
        super(theGameView);
        counter = 0;
        myShrinkAmount = theGameView.mBlockSize / 6;
    }

    /**
     * First get all entities with a position and movable.  Add the dX of the Moveable to the
     * X of the Position.  Do the same for Y.  Finally, update the HitBox of the entity based
     * on it's new position.  (Last step is only necessary for those that have moved, not
     * static items like mushrooms.)
     *
     * @param lastFrameTime The lastFrameTime from the previous call.
     */
    @Override
    public void processOneGameTick(long lastFrameTime) {
        counter += lastFrameTime;
            Set<UUID> allMove = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Movable.class);
            if(counter > 500) {
                mGameView.myMoveSegement = true;
                counter = 0;
            }
            for (UUID entityID : allMove) {
                Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
                Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
                float x = pos.getX();
                float y = pos.getY();
                if (mGameView.mEntityManager.hasComponent(entityID, Components.SegmentComponent.class)) {
                    Components.Direction dir = mGameView.mEntityManager.getComponent(entityID, Components.Direction.class);
                    if(mGameView.myMoveSegement) {
                        if (!mGameView.mEntityManager.hasComponent(entityID, Components.SegmentMovable.class)) {
                            if (pos.getX() + move.getDx() + mGameView.mBlockSize > mGameView.getmScreenSizeX()) { //if it hits the right side.
                                dir.collided = true;
                            } else if (pos.getX() + move.getDx() < 0) { //if it hits the left side.
                                dir.collided = true;
                            }

                            if (dir.collided) {
                                dir.setDir(!dir.getDir());
                                move.dy = mGameView.mBlockSize;
                                move.dx = 0;
                            } else {
                                move.dy = 0;
                            }
                            pos.setX(x + move.dx);
                            pos.setY(y + move.dy);
                            Components.HitBox hitBox = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);

                            x = pos.getX();
                            y = pos.getY();
                            Components.EntitySize es = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);
                            hitBox.setHitBox(
                                    x + myShrinkAmount,
                                    y + myShrinkAmount,
                                    x + (es.getEntityWidth() - myShrinkAmount),
                                    y + (es.getEntityHeight() - myShrinkAmount));
                            if (dir.collided) {
                                if (dir.getDir()) {
                                   move.dx = mGameView.mBlockSize;
    //                                move.dy = 0;
                                } else {
                                    move.dx = -mGameView.mBlockSize;
    //                                move.dy = 0;
                                }
                            }
                            dir.collided = false;
                        } else {

                            Components.SegmentMovable segmov = mGameView.mEntityManager.getComponent(entityID, Components.SegmentMovable.class);
                            pos.setX(x + segmov.dx);
                            pos.setY(y + segmov.dy);
                            x = pos.getX();
                            y = pos.getY();
                            Components.HitBox hitBox = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);

                            Components.EntitySize es = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);
                            hitBox.setHitBox(
                                            x + myShrinkAmount,
                                            y + myShrinkAmount,
                                            x + (es.getEntityWidth() - myShrinkAmount),
                                            y + (es.getEntityHeight() - myShrinkAmount));
                        }
                    }

                    continue;
                }
                if (move.dx != 0 || move.dy != 0) {
                    if (move.dx != 0) {
                        if ((x + move.getDx()) > 0 && (x + move.getDx()) < mGameView.getWidth()) {
                            pos.setX(x + move.dx);
                        }
                    }
                    if (move.dy != 0) {
                        if ((y + move.getDy()) > 0 && (y + move.getDy()) < mGameView.getHeight()) {
                            pos.setY(y + move.dy);
                        }
                    }
                    if (mGameView.mEntityManager.hasComponent(entityID, Components.HitBox.class)) {
                        if (mGameView.mEntityManager.hasComponent(entityID, Components.Touch.class)) {  //Code for the Ship.
                            pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
                            Components.HitBox hitBox = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);
                            Components.EntitySize es = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);

                            hitBox.setHitBox(
                                    (pos.getX() - es.getHalfWidth() / 2),
                                    (pos.getY() - es.getHalfHeight()),
                                    (pos.getX() + es.getHalfWidth() / 2),
                                    (pos.getY() + es.getHalfHeight()));
                        } else {
                            Components.HitBox hitBox = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);
                            Components.EntitySize es = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);
                            pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
                            x = pos.getX();
                            y = pos.getY();
                            hitBox.setHitBox(
                                    x,
                                    y,
                                    x + es.getEntityWidth(),
                                    y + es.getEntityHeight());
                        }
                    }
                }
            }
        }

    /**
     * Method used for getting the string name of the system.
     *
     * @return a string of the system.
     */
    @Override
    public String getSimpleName() {
        return "Movement";
    }
}