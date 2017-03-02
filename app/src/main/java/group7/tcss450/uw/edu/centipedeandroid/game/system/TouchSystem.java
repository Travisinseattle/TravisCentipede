package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.util.Log;

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

    private static final float MOVEMENT_DIVISOR = 5;
    private static final float CLOSE_ENOUGH = 20;


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

                //The distance between last position and current touch in X
                float differenceX = pos.getX() - mGameView.mTouchX;

                //The distance between last position and current touch in Y
                float differenceY = pos.getY() - mGameView.mTouchY;

                /**
                 * If the distance between touch and pos is small enough, stop movement.
                 * Otherwise, set the movement amount to be a fraction of that distance,
                 * provided that fraction is never larger than a block (needs to be negative
                 * since the cartesian grid has only positive values).
                 * The larger the distance, the bigger the movement which simulates speed.
                 * Regardless, the ship should not teleport, but instead move towards the
                 * touch.
                 */
                if (Math.abs(differenceX) < CLOSE_ENOUGH) {
                    move.setDx(0);
                } else {
                    move.setDx(Math.min(-(differenceX / MOVEMENT_DIVISOR), mGameView.mBlockSize));
                }

                if (Math.abs(differenceY) < CLOSE_ENOUGH) {
                    move.setDy(0);
                } else {
                    move.setDy(Math.min(-(differenceY / MOVEMENT_DIVISOR), mGameView.mBlockSize));
                }

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
