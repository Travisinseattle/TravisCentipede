package group7.tcss450.uw.edu.centipedeandroid.game;


import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 * This class defines how different entities are created and what components they would hold.
 */

public class EntityFactory {
//
//    private static float BLOCK_SIZE;
//    private static final float SHRINK_VALUE = BLOCK_SIZE / 6;


    /**
     * Method that creates a board entity.
     *
     * @return A MetaEntity object of the board.
     */
    public static MetaEntity createGameBoard() {
        MetaEntity board = new MetaEntity("board");
        return board;
    }

    /**
     * Method that creates a a mushroom entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior.
     *
     * @return A MetaEntity object of the mushroom.
     */
    public static MetaEntity createMushroom(float theBlockSize) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity("mushroom");
        mushroom.add(new Components.EntitySize(theBlockSize, theBlockSize));
        mushroom.add(new Components.Health(4));
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(R.drawable.shroom));
        return mushroom;
    }

    /**
     * Method that creates a a mushroom entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior.
     *
     * @param p is the position of the mushroom
     * @return A MetaEntity object of the mushroom.
     */
    public static MetaEntity createMushroom(Components.Position p, float theBlockSize) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity("mushroom");
        Components.EntitySize es = new Components.EntitySize(theBlockSize, theBlockSize);
        mushroom.add(es);
        mushroom.add(new Components.Health(4));
        mushroom.add(p);
        mushroom.add(new Components.Score(10));
        mushroom.add(new Components.DamagedDrawable(new int[] {R.drawable.shroom, R.drawable.shroom3, R.drawable.shroom2, R.drawable.shroom1}));
        mushroom.add(new Components.HitBox(
                p.getX() + theBlockSize / 6,
                p.getY() + theBlockSize / 6,
                p.getX() + (es.getEntityWidth() - theBlockSize / 6),
                p.getY() + (es.getEntityHeight() - theBlockSize / 6)));
        return mushroom;
    }

    /**
     * A static method to instantiate a player ship.
     *
     * @param theX The X coordinate of the ship.
     * @param theY The Y coordinate of the ship.
     * @param theBlockSize The size of a block in the screen grid.
     *
     * @return The player ship.
     */
    public static MetaEntity createShip(float theX, float theY, float theBlockSize) {
        Components.EntitySize es = new Components.EntitySize(theBlockSize * 2,
                theBlockSize);
//        Components.Position p = new Components.Position((GameActivity.mWidth / 2),
//                (GameActivity.mHeight - GameActivity.mBlockSize));
        Components.Position p = new Components.Position(theX, theY);
        MetaEntity ship = new MetaEntity("ship",
                p,
                es,
                new Components.CAndroidDrawable(R.drawable.alienblaster),
                new Components.Touch(),
                new Components.Movable(),
                new Components.HitBox(
                        p.getX() + theBlockSize / 6,
                        p.getY() + theBlockSize / 6,
                        p.getX() + (es.getEntityWidth() - theBlockSize / 6),
                        p.getY() + (es.getEntityHeight() - theBlockSize / 6)));
        return ship;
    }

    /**
     * Method that creates a bullet entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior. Bullet spawn is controlled by where the ship is.
     *
     * @param x is the x position of the bullet.
     * @param y is the y position of the bullet.
     * @return A MetaEntity object of the bullet.
     */
    public static MetaEntity createBullet(float x, float y, float theBlockSize) {
        Components.EntitySize es = new Components.EntitySize(theBlockSize/ 3,
                theBlockSize / 3);
        MetaEntity bullet = new MetaEntity("bullet", es,
                new Components.CAndroidDrawable(R.drawable.fireball),
                new Components.Health(1),
                new Components.Movable(0, -50),
                new Components.Position(x, y),
                new Components.Hazard(1),
                new Components.Shoot(),
                new Components.Team(Components.Team.Color.Blue),
                new Components.HitBox(x, y,
                        x + es.getEntityWidth(),
                        y + es.getEntityHeight()));
        return bullet;
    }

    public static MetaEntity createCentBody(float x, float y, UUID parent, float theBlockSize) {
        Components.EntitySize es = new Components.EntitySize(theBlockSize,
                theBlockSize);
        MetaEntity centBody = new MetaEntity("Centipede Body", es,
                new Components.Direction(true),
                new Components.Position(x,y),
                new Components.SegmentComponent(parent),
                new Components.Team(Components.Team.Color.Red),
                new Components.Health(1),
                new Components.HitBox(
                        x,
                        y,
                        x + es.getEntityWidth(),
                        y + es.getEntityHeight()));
        return centBody;
    }


    public static void splitCentipede(UUID[] theIDS, UUID theSeg) {

    }

    /**
     * Method that creates a centipede entity.
     * Entity is comprised of components that
     * control behavior. The centipede is made up of several segments.
     *
     * @param theGameView is a reference to the game view.
     * @param theSize is the number of segments in the centipede.
     * @return A MetaEntity object of the centipede.
     */
    public static MetaEntity createCentipede(GameView theGameView, int theSize) {
//        MetaEntity centipede = new MetaEntity();
        UUID[] ids = new UUID[theSize];
        int k = 0;
        MetaEntity centipede = new MetaEntity("Centipede");
        for (int i = 0; i<theSize; i++ ) {
            k += theGameView.mBlockSize;
            MetaEntity temp = EntityFactory.createCentBody((theGameView.mScreenSizeX)/2 - k, 0, centipede.entity, theGameView.mBlockSize);
            if (i == 0) {
                temp.add(new Components.Movable(theGameView.mBlockSize,0));
                temp.add(new Components.CAndroidDrawable(R.drawable.centipedehead));
                temp.add(new Components.Score(50));
            } else {
                Components.SegmentMovable sm = new Components.SegmentMovable();
                sm.dx = theGameView.mBlockSize;
                temp.add(new Components.CAndroidDrawable(R.drawable.centipede));
                temp.add(sm);
                temp.add(new Components.Score(25));
                temp.add(new Components.Movable(0,0));
            }
            ids[i] = temp.entity;
        }
        centipede.add(new Components.CentipedeID(ids));

        return centipede;
    }

    /**
     * Method that creates a centipede entity.
     * Entity is comprised of components that
     * control behavior. This centipede takes a list of
     * already created segements for a new centipede to be created.
     *
     * @param theIDS is a list of segement ids
     * @return A MetaEntity object of the centipede.
     */
    public static MetaEntity createCentipede(UUID[] theIDS) {
        MetaEntity centipede = new MetaEntity("centipede");
        centipede.add(new Components.CentipedeID(theIDS));
        return centipede;
    }

    /**
     * Method that creates a scorpian entity that will spawn
     * on the screen. Entity is comprised of components that
     * control behavior. Scorpian moves in a horizontal line on the screen.
     *
     * @param theGameView is a reference to the game view.
     * @param x is the x position of the scorpian.
     * @param y is the y position of the scorpian.
     * @return A MetaEntity object of the scorpian.
     */
    public static MetaEntity createScorpian(GameView theGameView, float x, float y) {
        MetaEntity scorpian = new MetaEntity("scorpion");
//        scorpian.add(new Components.EntitySize(GameActivity.getBlockSize() + 40,
//                GameActivity.getBlockSize() + 40)); // POSSIBLE CHANGE SIZE OF THIS CREATURE CURRENT NUMBERS ARE TEST NUMBERS
        scorpian.add(new Components.CAndroidDrawable(R.drawable.scorpianeast));
        scorpian.add(new Components.Movable(1/2,0));
        scorpian.add(new Components.Position(x,y));
//        scorpian.add(new Components.Health());
        return scorpian;
    }
}
