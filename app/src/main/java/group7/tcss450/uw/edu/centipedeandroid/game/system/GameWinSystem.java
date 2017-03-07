package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.menu.HighScore;
import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

/**
 * Class that determines if the game was won by checking if the centipede entity still exists.
 *
 * Created by nicholas on 3/3/17.
 */
public class GameWinSystem extends SubSystem {

    /**
     * The context of the gameview.
     */
    private final Context context = mGameView.mContext;

    /**
     * Constructor for the game win system.
     *
     * @param theGameView the view of the game.
     */
    public GameWinSystem(GameView theGameView) {
        super(theGameView);
    }

    /**
     * Method that process one game tick and checks if the centipede size is 0 if so then the game is
     * won.
     *
     * @param lastFrameTime is the most recent frame.
     */
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> segments = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.SegmentComponent.class);
        if (segments.size() <= 0) {
            Log.e("IT HAPPENED!!!", "*************************************IT SURE DID!***************************************");
            mGameView.gameWin();
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
