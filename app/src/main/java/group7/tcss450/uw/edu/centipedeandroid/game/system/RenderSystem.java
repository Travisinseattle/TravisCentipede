package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 * The System that will handle the process of rendering a game object
 */

public class RenderSystem extends SubSystem {

    private Bitmap mBitmap;

    public RenderSystem(GameView theGameView) {
        super(theGameView);
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.CAndroidDrawable.class);
        for (UUID entityID : allDrawables) {
            Components.Position pos = mGameView.mEntityManager
                    .getComponent(entityID, Components.Position.class);
            Components.EntitySize size = mGameView.mEntityManager
                    .getComponent(entityID, Components.EntitySize.class);
            mBitmap = mGameView.mEntityManager
                    .getComponent(entityID, Components.CAndroidDrawable.class).getBitMap();
            mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) size.getEntityWidth(),
                    (int) size.getEntityHeight(), false);
            mGameView.mCanvas.drawBitmap(mBitmap, pos.getX() - size.getHalfWidth(),
                    pos.getY() - size.getHalfHeight(), mGameView.mPaint);
        }
    }

    @Override
    public String getSimpleName() {
        return "Render";
    }
}
