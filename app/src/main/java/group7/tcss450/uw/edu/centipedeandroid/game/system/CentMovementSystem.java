package group7.tcss450.uw.edu.centipedeandroid.game.system;


import android.util.Log;

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
            Log.e("Position", entityID.toString() + ", Name: " + mGameView.mEntityManager.nameFor(entityID) + ", Position: (" + pos.getX() + ", " + pos.getY() + "), DX/DY: (" + move.getDx() + ", " + move.getDy() + ")");
            if ((pos.getX() + move.getDx()) > mGameView.getmScreenSizeX()) { //if it hits the right side.
                dir.swapDir(dir.getDir()); //swap direction boolean
                move.setDy(mGameView.mBlockSize/2); //set the change in Y to be half a block downwards.
                move.setDx(-MOVE_AMOUNT); //set the change in X to go left.
            } else if((pos.getX() + move.getDx()) < mGameView.mBlockSize / 2) { //if it hits the left side.
                dir.swapDir(dir.getDir()); //swap direction boolean
                move.setDy(mGameView.mBlockSize/2); //set the change in Y to be half a block downwards.
                move.setDx(MOVE_AMOUNT); //Set the change in X to go right.
            } else {
                move.setDy(0); //Make sure there is no change in Y leftover from a previous call to the system.
            }
        }
    }

    public static int getMoveAmmount() {
        return MOVE_AMOUNT;
    }

    @Override
    public String getSimpleName() {
        return "Centipede Movement";
    }
}


