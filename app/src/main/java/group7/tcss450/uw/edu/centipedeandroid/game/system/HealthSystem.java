package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * System that process the health of the entities.
 *
 * Created by Travis Holloway on 2/17/2017.
 */
public class HealthSystem extends SubSystem {

    /**
     * Health system constructor.
     *
     * @param theGameView
     */
    public HealthSystem(GameView theGameView) {
        super(theGameView);
    }

    /**
     * Health system that grabs all entities with the health component and processes their health.
     *
     * @param lastFrameTime is the current frame being processed.
     */
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHealth = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Health.class);
        for (UUID entityID : allHealth) {
            Components.Health health = mGameView.mEntityManager.getComponent(entityID, Components.Health.class);


        }
    }

    /**
     * Method used for getting the string name of the system.
     *
     * @return a string of the system.
     */
    @Override
    public String getSimpleName() {
        return "Health";
    }
}
