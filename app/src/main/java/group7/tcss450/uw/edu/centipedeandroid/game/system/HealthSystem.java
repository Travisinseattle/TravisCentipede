package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 */

public class HealthSystem extends SubSystem {

    public HealthSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHealth = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Health.class);
        for (UUID entityID : allHealth) {
            Components.Health health = mGameView.mEntityManager.getComponent(entityID, Components.Health.class);
            Components.Damage damage = mGameView.mEntityManager.getComponent(entityID, Components.Damage.class);

            health.setHitpoints(health.getHitpoints() - damage.getDamage());


        }
    }

    @Override
    public String getSimpleName() {
        return "Health";
    }
}
