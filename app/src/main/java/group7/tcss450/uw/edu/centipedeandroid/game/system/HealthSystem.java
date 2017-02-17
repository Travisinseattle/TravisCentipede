package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 */

public class HealthSystem extends SubSystem {

    public HealthSystem( gm) {
        super(gm);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHealth = this.mGameManager.mEntityManager.getAllEntitiesPossessingComponent( Components.Health.class );
        for( UUID entityID : allHealth )
        {
            Components.Health health = mGameView.mEntitySystem.getComponent(entityID, Components.Health.class);
            Components.Damage damage = entitySystem.getComponent(entityId, Components.Damage.class);

            health.setHitpoints(health.hitpoints - damage.amount);


        }

        @Override
        public String getSimpleName() {
            return "Health";
        }
}
