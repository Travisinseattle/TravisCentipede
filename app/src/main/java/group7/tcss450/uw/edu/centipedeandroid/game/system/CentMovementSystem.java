package group7.tcss450.uw.edu.centipedeandroid.game.system;


import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * A class to control the movement behavior of a centipede.
 */
public class CentMovementSystem extends SubSystem{
    private static final int MOVE_AMOUNT = 10;

    public CentMovementSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allMove = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Direction.class);
        for (UUID entityID : allMove) {
            Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            Components.Direction dir = mGameView.mEntityManager.getComponent(entityID, Components.Direction.class);
            if (pos.getX() > mGameView.getmScreenSizeX() - (mGameView.mBlockSize / 2)) {
                dir.swapDir(dir.getDir());
                pos.setY(pos.getY() + mGameView.mBlockSize/2);
                move.setDx(-MOVE_AMOUNT);
            } else if(pos.getX() < mGameView.mBlockSize / 2) {
                dir.swapDir(dir.getDir());
                pos.setY(pos.getY() + mGameView.mBlockSize/2);
                move.setDx(MOVE_AMOUNT);
            }
        }


    }

    @Override
    public String getSimpleName() {
        return "Centipede Movement";
    }
}


