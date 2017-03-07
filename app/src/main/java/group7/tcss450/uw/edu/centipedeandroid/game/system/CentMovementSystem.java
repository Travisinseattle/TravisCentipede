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
        Set<UUID> allCentipedes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CentipedeID.class);
        for (UUID entityID : allCentipedes) {
            Components.CentipedeID id = mGameView.mEntityManager.getComponent(entityID, Components.CentipedeID.class);
            for (int i = 0; i < id.myIDs.length; ++i) {
                UUID segmentBack = id.myIDs[i];
                Components.Movable movB = mGameView.mEntityManager.getComponent(segmentBack, Components.Movable.class);
                Components.Position pos = mGameView.mEntityManager.getComponent(segmentBack, Components.Position.class);
//                if (i > 0) {
//                    UUID segmentFront = id.myIDs[i - 1];
//                    Components.Movable movF = mGameView.mEntityManager.getComponent(segmentFront, Components.Movable.class);
//
//                    movB.setDx(movF.getDx());
//                    movB.setDy(movF.getDy());
//                }
//                    if (pos.getX() + movB.getDx() + mGameView.mBlockSize > mGameView.getmScreenSizeX()) { //if it hits the right side.
//                        if (movB.getDy() == mGameView.mBlockSize) {
//                            movB.setDx(-mGameView.mBlockSize);
//                            movB.setDy(0);
//                        }
//                        else {
//                            movB.setDy(mGameView.mBlockSize); //set the change in Y to be half a block downwards.
////                            movB.setDx(0); //set the change in X to go left.
//                        }
//                    } else if(pos.getX() + movB.getDx() < 0) { //if it hits the left side.
//                        if (movB.getDy() == mGameView.mBlockSize) {
//                            movB.setDx(mGameView.mBlockSize);
//                            movB.setDy(0);
//                        } else {
//                            movB.setDy(mGameView.mBlockSize); //set the change in Y to be half a block downwards.
////                            movB.setDx(0); //set the change in X to go left.
//                        }
//                    } else if (movB.getDy() == mGameView.mBlockSize) {
//
//                        movB.setDx(mGameView.mBlockSize);
//                        movB.setDy(0);
//                    }
//                    pos.setX(movB.getDx() + pos.getX());
//                pos.setY(movB.getDy() + pos.getY());
            }

        }
    }

//

//
//            Components.Direction dir = mGameView.mEntityManager.getComponent(entityID, Components.Direction.class);

//            Log.e("Position", entityID.toString() + ", Name: " + mGameView.mEntityManager.nameFor(entityID) + ", Position: (" + pos.getX() + ", " + pos.getY() + "), DX/DY: (" + move.getDx() + ", " + move.getDy() + ")");




    public static int getMoveAmmount() {
        return MOVE_AMOUNT;
    }

    @Override
    public String getSimpleName() {
        return "Centipede Movement";
    }
}


