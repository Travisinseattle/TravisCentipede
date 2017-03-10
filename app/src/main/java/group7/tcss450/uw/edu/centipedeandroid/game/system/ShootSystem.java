package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
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

    /** counter for SPEED control */
    private int counter;

    /** SPEED of the bullet */
    private final static int SPEED = 500;

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
    }
    /**
     * Method that process a game tick and creates a bullet entity based on the position of the ship.
     * The system also sets the SPEED of the bullet and the frequency at which they are created.
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
            if (counter > SPEED) {
                counter -= SPEED;
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
