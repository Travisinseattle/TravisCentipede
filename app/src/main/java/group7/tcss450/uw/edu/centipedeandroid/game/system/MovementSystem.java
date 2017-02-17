package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 */

public class MovementSystem extends SubSystem {

    public MovementSystem(GameManager gm) {
        super(gm);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHealth = this.mGameManager.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Movable.class);
        for (UUID entityID : allHealth) {
            Components.Movable move = entitySystem.getComponent(entityID, Components.Movable.class);
            Components.Position pos = entitySystem.getComponent(entityID, Components.Position.class);

            pos.setX(pos.getX() + move.dx);
            pos.setY(pos.getY() + move.dy);
        }
    }

    @Override
    public String getSimpleName() {
        return "Health";
    }
}
