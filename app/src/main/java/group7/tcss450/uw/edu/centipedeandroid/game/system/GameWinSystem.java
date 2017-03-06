package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.HighScore;
import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

/**
 * Created by nicholas on 3/3/17.
 */

public class GameWinSystem extends SubSystem {

    private final Context context = mGameView.mContext;

    public GameWinSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> centipedes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CentipedeID.class);
        if (centipedes.size() == 0) {
            updateScores(); //updates the sharedpreferences file with the new score.
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }

    /**
     * A private method to update the shared preferences with the new player Score.
     */
    private void updateScores() {
        List<HighScore> scores = MenuActivity.getHighScores(context,
                context.getString(R.string.scores_list)); //Get the old list of scores.
        scores.add(new HighScore(mGameView.getmScore(), new Date())); //Add new HighScore object to list.

        PriorityQueue<HighScore> queue = new PriorityQueue<>(); //Sort the list.
        for (HighScore temp : scores) {
            queue.add(temp);
        }

        scores = new ArrayList<>(queue); //create a new array using the sorted list.

        MenuActivity.saveHighScores(context, context.getString(R.string.scores_list),
                scores); //Pass the list to the method for saving preferences.
    }
}
