package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by nicholas on 2/13/17.
 * The System that will handle the process of rendering a game object
 */

public class RenderSystem extends SubSystem {

    public RenderSystem(GameManager gm) {
        super(gm);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = this.mGameManager.mEntityManager.getAllEntitiesPossessingComponent( Components.CAndroidDrawable.class );
        for( UUID entityID : allDrawables )
        {
            Components.Position pos = entitySystem.getComponent(entityID, Components.Position.class );
            int resourceID = entitySystem.getComponent(entityID, Components.CAndroidDrawable.class ).resourceID;


        }

    @Override
    public String getSimpleName() {
        return "Render";
    }
}
