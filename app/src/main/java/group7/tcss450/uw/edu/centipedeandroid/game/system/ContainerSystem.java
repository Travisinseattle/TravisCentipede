package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/3/17.
 */

public class ContainerSystem extends SubSystem {

    public ContainerSystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allContained = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Contained.class);
        for (UUID entity: allContained) {
            Components.Contained contained = mGameView.mEntityManager.getComponent(entity, Components.Contained.class);
            Components.HitBox containedHit = mGameView.mEntityManager.getComponent(entity, Components.HitBox.class);
            Components.Container container = mGameView.mEntityManager.getComponent(contained.myContainer, Components.Container.class);
            if (!container.mySpace.contains(containedHit.getHitBox())) {
                Components.Movable containedMov = mGameView.mEntityManager.getComponent(entity, Components.Movable.class);
                Components.Position containedPos = mGameView.mEntityManager.getComponent(entity, Components.Position.class);
                containedPos.setX(containedPos.x - containedMov.dx);
                containedPos.setY(containedPos.y - containedMov.dy);
                containedMov.dx = 0;
                containedMov.dy = 0;
                if(mGameView.mEntityManager.hasComponent(entity, Components.CentipedeID.class)) {
                    // I am a centipede, I am a special flower!
                }
            }
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
