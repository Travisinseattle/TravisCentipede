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
        Set<UUID> allMove = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Movable.class);
        for (UUID entityID : allMove) {
            Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);

            pos.setX(pos.getX() + move.dx);
            pos.setY(pos.getY() + move.dy);
        }

    }

    @Override
    public String getSimpleName() {
        return "Health";
    }
}
