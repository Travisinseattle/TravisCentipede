package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by Travis Holloway on 2/22/2017.
 * A system to shoot bullets.
 */

public class ShootSystem extends SubSystem {

    private final static int MOD_RATE = 2;
    private int counter;
    private int speed;
    private List<UUID> killList;


    public ShootSystem(GameView theGameView) {
        super(theGameView);
        counter = 0;
        speed = theGameView.getBulletSpeed();
    }

    @Override
    public void processOneGameTick(long lastFrameTime) {
        counter += lastFrameTime;
        killList = new ArrayList<UUID>();

        Set<UUID> allTouch = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Touch.class);
        for (UUID entityID : allTouch) {
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            if (counter > speed) {
                counter -= speed;
                EntityFactory.createBullet(mGameView, pos.getX(), pos.getY() );
            }
        }

        Set<UUID> allShoot = mGameView.mEntityManager
                .getAllEntitiesPossessingComponent(Components.Shoot.class);
        for (UUID entityID : allShoot) {
            Components.Position pos = mGameView.mEntityManager.getComponent(entityID, Components.Position.class);
            if (pos.getY() < mGameView.mBlockSize ) {
                killList.add(entityID);
            }
        }

        for (UUID entityID : killList) {
            mGameView.mEntityManager.killEntity(entityID);
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
