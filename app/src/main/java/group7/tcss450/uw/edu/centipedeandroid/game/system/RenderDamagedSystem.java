package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

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

    public RenderDamagedSystem(GameView theGameView) {
        super(theGameView);
    }


    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allDrawables = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.CAndroidDrawable.class);
        for (UUID entityID : allDrawables) {
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            Components.Damage damage = mGameView.mEntityManager.getComponent(entityID, Components.Damage.class);
            int resourceIDs[] = mGameView.mEntityManager.getComponent(entityID, Components.DamagedDrawable.class).resourceID;
            int value = damage.getDamage();
            if (value > resourceIDs.length - 1) {
                value = resourceIDs.length - 1;
            }
            mBitmap = BitmapFactory.decodeResource(mGameView.mContext.getResources(), resourceIDs[value]);
            mGameView.mCanvas.drawBitmap(mBitmap, pos.getX(), pos.getY(), mGameView.mPaint);


        }
    }

    @Override
    public String getSimpleName() {
        return "Damaged Render";
    }
}
