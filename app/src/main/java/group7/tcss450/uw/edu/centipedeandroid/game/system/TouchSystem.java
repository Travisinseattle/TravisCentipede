package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Created by Travis Holloway on 2/17/2017.
 * System used to update the movable of a player ship.
 */

public class TouchSystem extends SubSystem {


    public TouchSystem(GameView theGameManager) {
        super(theGameManager);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allTouch = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Touch.class);
        for (UUID entityID : allTouch) {
            Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            if (mGameView.mShipMovement) {
                if (mGameView.mTouchX < pos.getX()) {
                    move.setDx(-25.f);
                }

                if (mGameView.mTouchX > pos.getX()) {
                    move.setDx(25.f);
                }

                if (mGameView.mTouchY > pos.getY()) {
                    move.setDy(25.f);
                }

                if (mGameView.mTouchY < pos.getY()) {
                    move.setDy(-25.f);
                }

//                move.setDx(mGameView.mTouchX - pos.getX());
//                move.setDy(mGameView.mTouchY - pos.getY());
            } else {
                move.setDx(0);
                move.setDy(0);
            }
        }
    }

    @Override
    public String getSimpleName() {
        return "Touch";
    }
}
