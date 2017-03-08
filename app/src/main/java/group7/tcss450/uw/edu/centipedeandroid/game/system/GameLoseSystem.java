package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.menu.HighScore;
import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

/**
 * Class that determines if the game was lost by checking if the player entity still exists.
 *
 * Created by nicholas on 3/3/17.
 */
public class GameLoseSystem extends SubSystem {

    private final int BOUNDRY = mGameView.getmScreenSizeY() - (mGameView.mBlockSize * 3);

    /**
     * The context of the gameview.
     */
    private final Context context = mGameView.mContext;

    /**
     * Constructor for the game lose system.
     *
     * @param theGameView the view of the game.
     */
    public GameLoseSystem(GameView theGameView) {
        super(theGameView);
    }

    /**
     * Method that process one game tick and checks if the player size is 0, if so then the game is
     * lost.
     *
     * @param lastFrameTime is the most recent frame.
     */
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> player = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Score.class);
        for (UUID entityID : player) {
            if (mGameView.mEntityManager.hasComponent(entityID, Components.Movable.class)) {
                Components.Movable move = mGameView.mEntityManager.getComponent(entityID, Components.Movable.class);
                Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);

                if (pos.getY() + move.getDy() > BOUNDRY) { // if the centipede enters the player zone.
                    mGameView.gameWin();
                }
            }
        }
    }
    /**
     * Method used for getting the string name of the system.
     *
     * @return a string of the system.
     */
    @Override
    public String getSimpleName() {
        return null;
    }

}
