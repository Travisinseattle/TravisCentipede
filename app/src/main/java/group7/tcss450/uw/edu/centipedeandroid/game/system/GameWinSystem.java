package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> segments = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.SegmentComponent.class);
        if (segments.size() <= 0 ) { //All current Centipedes have been killed.
            if (mGameView.mCurrentCount >= mGameView.CENT_COUNT) { //The player has killed more than the set amount of spawnable centipedes
                mGameView.gameWin(); //win game
            } else {
                mGameView.mCurrentCount++; //Increment the counter for how many centipedes killed.
                mGameView.updateLevel(); //spawn a new map and centipede
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
