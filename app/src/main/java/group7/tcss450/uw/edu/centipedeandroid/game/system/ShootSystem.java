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

    /** the rate at which bullets shoot out */
    private final static int MOD_RATE = 2;

    /** counter for speed control */
    private int counter;

    /** speed of the bullet */
    private int speed;

    /** list of UUID's */
    private List<UUID> killList;

    /**
     * Constructor for the system, sets fields.
     *
     * @param theGameView
     */
    public ShootSystem(GameView theGameView) {
        super(theGameView);
        counter = 0;
        speed = theGameView.getBulletSpeed();
    }
    /**
     * Method that process a game tick and creates a bullet entity based on the position of the ship.
     * The system also sets the speed of the bullet and the frequency at which they are created.
     *
     * @param lastFrameTime is the frame being processed.
     */
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
                EntityFactory.createBullet(pos.getX(), pos.getY(), mGameView.mBlockSize);
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
