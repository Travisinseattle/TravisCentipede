package group7.tcss450.uw.edu.centipedeandroid.game.system;

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

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHealth = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Movable.class);
        for (UUID entityID : allHealth) {
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
