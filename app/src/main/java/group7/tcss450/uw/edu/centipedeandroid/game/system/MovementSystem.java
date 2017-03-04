package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.util.Log;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 * A system to control movement
 */

public class MovementSystem extends SubSystem {

    public MovementSystem(GameView theGameView) {
        super(theGameView);
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
        Set<UUID> allMove = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Movable.class);
        for (UUID entityID : allMove) {
            Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            float x = pos.getX();
            float y = pos.getY();
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
                    Components.HitBox hitBox = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);

                    x = pos.getX();
                    y = pos.getY();
                    Components.EntitySize es = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);
                    hitBox.setHitBox(x, y, x + es.getEntityWidth(), y + es.getEntityHeight());

                }

            }

        }

    }

    @Override
    public String getSimpleName() {
        return "Movement";
    }
}
