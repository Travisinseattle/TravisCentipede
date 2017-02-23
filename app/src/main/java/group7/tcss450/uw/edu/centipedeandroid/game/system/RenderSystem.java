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

    public RenderSystem(GameView theGameView) {
        super(theGameView);
    }
    Bitmap mBitmap;

    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CAndroidDrawable.class);
        for (UUID entityID : allDrawables) {
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            int resourceID = mGameView.mEntityManager.getComponent(entityID, Components.CAndroidDrawable.class).resourceID;


                try {
                    mBitmap = BitmapFactory.decodeResource(mGameView.mContext.getResources(), resourceID);
                    mBitmap = Bitmap.createScaledBitmap(mBitmap, mGameView.mBlockSize, mGameView.mBlockSize, false);
                } catch (NullPointerException e) {
                    Log.e("Bitmap: ", e.getMessage());
                }


                if (mGameView.mPaint == null) {
                    Log.e("They are Null", "it happened");
                }

                /**
                 * Draw the mPlayShip.
                 */
                    mGameView.mCanvas.drawBitmap(mBitmap,
                            pos.getX() - (mGameView.mBlockSize / 2),
                            pos.getY() - (mGameView.mBlockSize / 2),
                            mGameView.mPaint);

        }
    }

    @Override
    public String getSimpleName() {
        return "Render";
    }
}
