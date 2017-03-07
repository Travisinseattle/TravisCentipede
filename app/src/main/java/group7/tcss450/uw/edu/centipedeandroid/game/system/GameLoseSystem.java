package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Class that determines if the game was lost by checking if the player entity still exists.
 *
 * Created by nicholas on 3/3/17.
 */
public class GameLoseSystem extends SubSystem {

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
        Set<UUID> player = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Touch.class);
        if (player.size() == 0) {
//            mGameView.gameLose();
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
