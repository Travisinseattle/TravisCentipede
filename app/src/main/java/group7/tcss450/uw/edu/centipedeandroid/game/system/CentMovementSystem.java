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
        if (mGameView.myMoveSegement) {
        Set<UUID> allCentipedes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CentipedeID.class);
        for (UUID entityID : allCentipedes) {
            Components.CentipedeID id = mGameView.mEntityManager.getComponent(entityID, Components.CentipedeID.class);
            if (id.myIDs.length > 1) {
                for (int i = id.myIDs.length - 1; i > 0; --i) {
                    UUID segmentBack = id.myIDs[i];
                    Components.SegmentMovable movB = mGameView.mEntityManager.getComponent(segmentBack, Components.SegmentMovable.class);
                    Components.Position pos = mGameView.mEntityManager.getComponent(segmentBack, Components.Position.class);
                    UUID segmentFront = id.myIDs[i - 1];
                    if (i == 1) {
                        Components.Movable movF = mGameView.mEntityManager.getComponent(segmentFront, Components.Movable.class);
                        movB.dx = movF.getDx();
                        movB.dy = movF.getDy();
                        if (movB.dy == mGameView.mBlockSize) {
                            movB.dx = 0;
                        }
                    } else {
                        Components.SegmentMovable movF = mGameView.mEntityManager.getComponent(segmentFront, Components.SegmentMovable.class);
                        float test1 = movF.dx;
                        float test2 = movF.dy;
                        movB.dx = test1;
                        movB.dy = test2;
                        if (movB.dy == mGameView.mBlockSize) {
                            movB.dx = 0;
                        }
                    }
                }

            }
        }
            mGameView.myMoveSegement = false;
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


