package group7.tcss450.uw.edu.centipedeandroid.game;


import java.util.Random;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 */

public class EntityFactory {

    public static MetaEntity createCentipede(int theSize) {
        MetaEntity centipede = new MetaEntity();
        UUID[] segments = new UUID[theSize];
        for (int i = 0; i < theSize; i++) {
            segments[i] = createCentipedeSegment(centipede.entity).entity;
        }
        centipede.add(new Components.ParentComponent(segments));
        return centipede;
    }

    public static MetaEntity createCentipede(UUID[] theSegments) {
        MetaEntity centipede = new MetaEntity();
        centipede.add(new Components.ParentComponent(theSegments));
        return centipede;
    }

    public static MetaEntity createCentipedeSegment(UUID theParent) {
        MetaEntity segment = new MetaEntity();
        segment.add(new Components.EntitySize());
        segment.add(new Components.Health());
        segment.add(new Components.Damage());
        segment.add(new Components.Movable());
        segment.add(new Components.SegmentComponent(theParent));
        return segment;
    }

    public static MetaEntity createMushroom() { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        mushroom.add(new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize()));
        mushroom.add(new Components.Health());
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(R.drawable.shroom));
        return mushroom;
    }

    public static MetaEntity createMushroom(GameView theGameView, Components.Position p) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize());
        mushroom.add(es);
        mushroom.add(new Components.Health());
        mushroom.add(new Components.Damage(new Random().nextInt(3))); // for now lets give the mushroom some random damage
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
        ship.add(new Components.Health());
        ship.add(new Components.HitBox());
        return ship;
    }

    public static MetaEntity createBullet(GameView theGameView, float x, float y) {
        MetaEntity bullet = new MetaEntity();
        bullet.add(new Components.EntitySize(GameActivity.getBlockSize() / 2,
                GameActivity.getBlockSize() / 2));
        bullet.add(new Components.CAndroidDrawable(R.drawable.fireball));
        bullet.add(new Components.Movable(0, - 100));
                //GameActivity.getBlockSize()));
        bullet.add(new Components.Position(x, y));
        bullet.add(new Components.Health());
        bullet.add(new Components.Shoot());
        return bullet;
    }

}
