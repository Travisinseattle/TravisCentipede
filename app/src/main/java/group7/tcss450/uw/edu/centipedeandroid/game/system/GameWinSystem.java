package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/3/17.
 */

public class GameWinSystem extends SubSystem {

    public GameWinSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> centipedes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CentipedeID.class);
        if (centipedes.size() == 0) {
            //mGameView.gameWin();
        }

    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
