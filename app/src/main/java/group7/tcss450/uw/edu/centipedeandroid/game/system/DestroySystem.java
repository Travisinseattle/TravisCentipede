package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.EntityManager;

/**
 * Created by nicholas on 3/2/17.
 */

public class DestroySystem extends SubSystem {

    public DestroySystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allAlive = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Health.class);
        Iterator<UUID> alive = allAlive.iterator();
        while (alive.hasNext()) {
            UUID entity = alive.next();
            Components.Health health = mGameView.mEntityManager.getComponent(entity, Components.Health.class);
            if (health.getHitPoints() <= 0) {
                if (mGameView.mEntityManager.hasComponent(entity, Components.Direction.class)) {
                    mGameView.mEntityManager.killEntity(mGameView.mEntityManager.getComponent(entity,Components.SegmentComponent.class).myParentEntity);
//                    EntityFactory.createMushroom(mGameView.mEntityManager.getComponent(entity, Components.Position.class));
//                    UUID[] ids = mGameView.mEntityManager.getComponent(entity, Components.CentipedeID.class).myIDs;

//                    EntityFactory.splitCentipede(ids, );

                }
                alive.remove();
                mGameView.mEntityManager.killEntity(entity);
            }

        }
//        for (UUID alive : allAlive) {
//            Components.Health health = mGameView.mEntityManager.getComponent(alive, Components.Health.class);
//            if (health.getHitPoints() == 0) {
//                mGameView.mEntityManager.killEntity(alive);
//            }
//        }

    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
