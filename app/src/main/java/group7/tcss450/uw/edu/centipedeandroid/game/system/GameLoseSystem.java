package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/3/17.
 */

public class GameLoseSystem extends SubSystem {

    public GameLoseSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> player = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Touch.class);
        if (player.size() == 0) {
            mGameView.gameLose();
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
