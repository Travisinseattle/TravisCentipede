package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.RectF;
import android.util.Log;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/28/17.
 */

public class PhysicsSystem extends SubSystem {
    public PhysicsSystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHitBoxes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.HitBox.class);
        for (UUID id: allHitBoxes) {
            if (!mGameView.mEntityManager.hasComponent(id, Components.Collision.class)) {
                if (mGameView.mEntityManager.hasComponent(id, Components.Movable.class)) {

                    Components.HitBox box = mGameView.mEntityManager.getComponent(id, Components.HitBox.class);
                    for (UUID otherID : allHitBoxes) {
                        if (otherID.equals(id))
                            continue;
                        Components.HitBox otherBox = mGameView.mEntityManager.getComponent(otherID, Components.HitBox.class);
                        //                        if (otherBox.getHitBox().intersect(box.getHitBox())) {
                        if (RectF.intersects(box.getHitBox(), otherBox.getHitBox())) {
                            mGameView.mEntityManager.addComponent(id, new Components.Collision(otherID));
                        }

                    }
                } else {
                    Components.HitBox shroomBox = mGameView.mEntityManager.getComponent(id, Components.HitBox.class);
                    for (UUID otherID : allHitBoxes) {
                        if (otherID.equals(id))
                            continue;
                        if (mGameView.mEntityManager.hasComponent(otherID, Components.Direction.class)) {
                            Components.HitBox otherBox = mGameView.mEntityManager.getComponent(otherID, Components.HitBox.class);
                            if (RectF.intersects(shroomBox.getHitBox(), otherBox.getHitBox())) {
                                //shroom collision.
                                Log.e("ShroomCollision", "id: (" + id + ", " + shroomBox.getHitBox().toShortString() +
                                        "), otherID: (" + otherID + ", " + otherBox.getHitBox().toShortString() + ")");
                                Components.Movable move = mGameView.mEntityManager.getComponent(otherID, Components.Movable.class);
                                Components.Position pos = mGameView.mEntityManager.getComponent(otherID, Components.Position.class);
                                Components.Direction dir = mGameView.mEntityManager.getComponent(otherID, Components.Direction.class);
                                if (move.getDx() >= 0) { //Moving to the right.
                                    dir.swapDir(dir.getDir()); //swap direction boolean
                                    pos.setX(pos.getX() - 20);
                                    move.setDy(mGameView.mBlockSize / 2); //set the change in Y to be half a block downwards.
                                   // move.setDx(-(CentMovementSystem.getMoveAmmount()));
                                    move.setDx(-10);
                                } else if (move.getDx() < 0){
                                    dir.swapDir(dir.getDir()); //swap direction boolean
                                    pos.setX(pos.getX() + 20);
                                    move.setDy(mGameView.mBlockSize / 2); //set the change in Y to be half a block downwards.
                                    //move.setDx((CentMovementSystem.getMoveAmmount()));
                                    move.setDx(10);
                                } else {
                                    move.setDy(0); //Make sure there is no change in Y leftover from a previous call to the system.
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
