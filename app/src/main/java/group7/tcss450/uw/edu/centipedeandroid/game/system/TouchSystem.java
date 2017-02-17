package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 * System used to update the movable of a player ship.
 */

public class TouchSystem extends SubSystem {


    public TouchSystem(GameManager theGameManager) {
        super(theGameManager);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allTouch = this.mGameManager.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Touch.class);
        for (UUID entityID : allTouch) {
            Components.Movable move = entitySystem.getComponent(entityID, Components.Movable.class);
            Components.Position pos = entitySystem.getComponent(entityID, Components.Position.class);
            if (mGameView.mShipMovement) {
                move.setDx(mGameView.mTouchX - pos.getX());
                move.setDy(mGameView.mTouchY - pos.getY());
            }
        }
    }

    @Override
    public String getSimpleName() {
        return "Touch";
    }
}
