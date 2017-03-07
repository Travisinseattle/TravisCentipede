package group7.tcss450.uw.edu.centipedeandroid.game.system;

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
        Set<UUID> centipedes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CentipedeID.class);
        if (centipedes.size() == 0) {
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
