package group7.tcss450.uw.edu.centipedeandroid.game;


import android.view.View;

import java.util.Random;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 */

public class EntityFactory {

//    public static MetaEntity createCentipede(int theSize) {
//        MetaEntity centipede = new MetaEntity();
//        UUID[] segments = new UUID[theSize];
//        for (int i = 0; i < theSize; i++) {
//            segments[i] = createCentipedeSegment(centipede.entity).entity;
//        }
//        centipede.add(new Components.ParentComponent(segments));
//        return centipede;
//    }
//
//    public static MetaEntity createCentipede(UUID[] theSegments) {
//        MetaEntity centipede = new MetaEntity();
//        centipede.add(new Components.ParentComponent(theSegments));
//        return centipede;
//    }

//    public static MetaEntity createCentipedeSegment(UUID theParent) {
//        MetaEntity segment = new MetaEntity();
//        segment.add(new Components.EntitySize());
//        segment.add(new Components.Health());
//        segment.add(new Components.Damage());
//        segment.add(new Components.Movable());
//        segment.add(new Components.SegmentComponent(theParent));
//        return segment;
//    }

    public static MetaEntity createGameBoard() {
        MetaEntity board = new MetaEntity();
        return board;
    }

    public static MetaEntity createMushroom() { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        mushroom.add(new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize()));
        mushroom.add(new Components.Health(4));
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(R.drawable.shroom));
        return mushroom;
    }

    public static MetaEntity createMushroom(Components.Position p) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize());
        mushroom.add(es);
        mushroom.add(new Components.Health(4));
//        mushroom.add(new Components.Damage(new Random().nextInt(3))); // for now lets give the mushroom some random damage
        mushroom.add(p);
        mushroom.add(new Components.DamagedDrawable(new int[] {R.drawable.shroom, R.drawable.shroom3, R.drawable.shroom2, R.drawable.shroom1}));
        mushroom.add(new Components.HitBox(p.getX(), p.getY(), p.getX() + es.getEntityWidth(), p.getY() + es.getEntityHeight()));
        return mushroom;
    }

    public static MetaEntity createShip(GameView theGameView) {
        MetaEntity ship = new MetaEntity();
        ship.add(new Components.EntitySize(GameActivity.getBlockSize() * 2,
                GameActivity.getBlockSize()));
        ship.add(new Components.CAndroidDrawable(R.drawable.alienblaster));
        ship.add(new Components.Touch());
        ship.add(new Components.Movable());
        ship.add(new Components.Position((GameActivity.mWidth / 2),  (GameActivity.mHeight -
                GameActivity.mBlockSize)));
//        ship.add(new Components.Health());
//        ship.add(new Components.HitBox());
        return ship;
    }

    public static MetaEntity createBullet(GameView theGameView, float x, float y) {
        MetaEntity bullet = new MetaEntity();
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize() / 2,
                GameActivity.getBlockSize() / 2);
        bullet.add(es);
        bullet.add(new Components.CAndroidDrawable(R.drawable.fireball));
        bullet.add(new Components.Health(1));
        bullet.add(new Components.Movable(0, - 100));
                //GameActivity.getBlockSize()));
        bullet.add(new Components.Position(x, y));
        bullet.add(new Components.Hazard(1));
        bullet.add(new Components.Shoot());
        bullet.add(new Components.HitBox(x, y, x + es.getEntityWidth(), y + es.getEntityHeight()));

        return bullet;
    }

    public static MetaEntity createCentBody(GameView theGameView, float x, float y, UUID theParent) {
        MetaEntity centBody = new MetaEntity();
        centBody.add(new Components.EntitySize(GameActivity.getBlockSize() / 2,
                GameActivity.getBlockSize() / 2));
        centBody.add(new Components.CAndroidDrawable(R.drawable.centipede));
        centBody.add(new Components.Movable(10,0));
        centBody.add(new Components.Direction(true));
        centBody.add(new Components.Position(x,y));
        centBody.add(new Components.SegmentComponent(theParent));
        return centBody;
    }

    public static MetaEntity createCentipede(GameView theGameView, int theSize) {
        MetaEntity centipede = new MetaEntity();
        UUID[] ids = new UUID[theSize];
        int k = 0;
        for (int i = 0; i<theSize; i++ ) {
            k -= theGameView.mBlockSize/2;
            MetaEntity temp = EntityFactory.createCentBody(theGameView, (theGameView.mScreenSizeX)/2-k, -50, centipede.entity);
            ids[i] = temp.entity;
        }
        centipede.add(new Components.CentipedeID(ids));
        //centipede.add(new Components.SegmentParent());
        return centipede;
    }

    public static MetaEntity createCentipede(UUID[] theIDS) {
        MetaEntity centipede = new MetaEntity();
        centipede.add(new Components.CentipedeID(theIDS));
        return centipede;
    }

    public static MetaEntity createScorpian(GameView theGameView, float x, float y) {
        MetaEntity scorpian = new MetaEntity();
        scorpian.add(new Components.EntitySize(GameActivity.getBlockSize() + 40,
                GameActivity.getBlockSize() + 40)); // POSSIBLE CHANGE SIZE OF THIS CREATURE CURRENT NUMBERS ARE TEST NUMBERS
        scorpian.add(new Components.CAndroidDrawable(R.drawable.scorpianeast));
        scorpian.add(new Components.Movable(1/2,0));
        scorpian.add(new Components.Position(x,y));
//        scorpian.add(new Components.Health());
        return scorpian;
    }
}
