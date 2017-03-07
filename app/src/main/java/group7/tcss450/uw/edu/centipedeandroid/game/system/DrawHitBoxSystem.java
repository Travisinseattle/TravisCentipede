package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/2/17.
 */

public class DrawHitBoxSystem extends SubSystem {

    public DrawHitBoxSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.HitBox.class);
        for (UUID entityID : allDrawables) {
            Components.HitBox hit = mGameView.mEntityManager.getComponent(entityID, Components.HitBox.class);

            //mGameView.mCanvas.drawRect(hit.getHitBox(), mGameView.mPaint);
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
