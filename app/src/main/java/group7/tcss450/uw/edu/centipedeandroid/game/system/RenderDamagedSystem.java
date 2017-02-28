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
 * Created by nicholas on 2/22/17.
 */

public class RenderDamagedSystem extends SubSystem {
    private Bitmap mBitmap;
    private HashMap<Integer, Bitmap> scaledBitmaps;

    public RenderDamagedSystem(GameView theGameView) {
        super(theGameView);
        scaledBitmaps = new HashMap<>();
    }


    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.DamagedDrawable.class);
        for (UUID entityID : allDrawables) {
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            Components.Damage damage = mGameView.mEntityManager.getComponent(entityID, Components.Damage.class);
            Components.EntitySize size = mGameView.mEntityManager.getComponent(entityID, Components.EntitySize.class);
            int resourceIDs[] = mGameView.mEntityManager.getComponent(entityID, Components.DamagedDrawable.class).resourceID;
            int value = damage.getDamage();
            if (value > resourceIDs.length - 1) {
                value = resourceIDs.length - 1;
            }
            int resID = resourceIDs[value];
            mBitmap = scaledBitmaps.get(resID);
            if (mBitmap == null) {
                mBitmap = BitmapFactory.decodeResource(mGameView.getResources(), resID);
                mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) size.getEntityWidth(), (int) size.getEntityHeight(), false);
                scaledBitmaps.put(resID, mBitmap);
            }
            mGameView.mCanvas.drawBitmap(mBitmap, pos.getX(), pos.getY(), mGameView.mPaint);


        }
    }

    @Override
    public String getSimpleName() {
        return "Damaged Render";
    }
}
