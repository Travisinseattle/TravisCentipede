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

    private static final float SHRINK_VALUE = GameActivity.getBlockSize() / 6;

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
        MetaEntity board = new MetaEntity("board");
        return board;
    }

    public static MetaEntity createMushroom() { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity("mushroom",
                new Components.EntitySize(GameActivity.getBlockSize(),
                        GameActivity.getBlockSize()),
                new Components.Health(4),
                new Components.Position(),
                new Components.CAndroidDrawable(R.drawable.shroom));

        return mushroom;
    }

    public static MetaEntity createMushroom(Components.Position p) { // Mushrooms will look like aliens for now.
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize());
        MetaEntity mushroom = new MetaEntity("mushroom", es, p,
                new Components.Health(4),
                new Components.DamagedDrawable(
                        new int[] {R.drawable.shroom,
                        R.drawable.shroom3,
                        R.drawable.shroom2,
                        R.drawable.shroom1}),
                new Components.HitBox(
                        p.getX() + SHRINK_VALUE,
                        p.getY() + SHRINK_VALUE,
                        p.getX() + (es.getEntityWidth() - SHRINK_VALUE),
                        p.getY() + (es.getEntityHeight() - SHRINK_VALUE)));
        return mushroom;
    }

    public static MetaEntity createShip() {
        MetaEntity ship = new MetaEntity("ship",
                new Components.EntitySize(GameActivity.getBlockSize() * 2,
                        GameActivity.getBlockSize()),
                new Components.CAndroidDrawable(R.drawable.alienblaster),
                new Components.Touch(),
                new Components.Movable(),
                new Components.Position((GameActivity.mWidth / 2),
                        (GameActivity.mHeight -
                        GameActivity.mBlockSize)));
        return ship;
    }

    public static MetaEntity createBullet(float x, float y) {
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize() / 2,
                GameActivity.getBlockSize() / 2);
        MetaEntity bullet = new MetaEntity("bullet", es,
                new Components.CAndroidDrawable(R.drawable.fireball),
                new Components.Health(1),
                new Components.Movable(0, - 100),
                new Components.Position(x, y),
                new Components.Hazard(1),
                new Components.Shoot(),
                new Components.HitBox(x, y,
                        x + es.getEntityWidth(),
                        y + es.getEntityHeight()));
        return bullet;
    }

    public static MetaEntity createCentBody(float x, float y) {
        Components.EntitySize es = new Components.EntitySize(GameActivity.getBlockSize() /2,
                GameActivity.getBlockSize() / 2);
        MetaEntity centBody = new MetaEntity("Centepede Body", es,
                new Components.CAndroidDrawable(R.drawable.centipede),
                new Components.Movable(10,0),
                new Components.Direction(true),
                new Components.Position(x,y),
                new Components.Health(1),
                new Components.HitBox(x, y,
                        x + es.getEntityWidth(),
                        y + es.getEntityHeight()));
        return centBody;
    }

    public static MetaEntity createCentipede(GameView theGameView, int theSize) {
        UUID[] ids = new UUID[theSize];
        int k = 0;
        for (int i = 0; i<theSize; i++ ) {
            k -= theGameView.mBlockSize;
//            MetaEntity temp = EntityFactory.createCentBody((theGameView.mScreenSizeX)/2-k, -theGameView.mBlockSize);
            MetaEntity temp = EntityFactory.createCentBody((theGameView.mScreenSizeX)/2-k, 50);
            ids[i] = temp.entity;
        }
        MetaEntity centipede = new MetaEntity("Centipede",
                new Components.CentipedeID(ids));
        return centipede;
    }

    public static MetaEntity createCentipede(UUID[] theIDS) {
        MetaEntity centipede = new MetaEntity("Centipede",
                new Components.CentipedeID(theIDS));
        return centipede;
    }

    public static MetaEntity createScorpian(GameView theGameView, float x, float y) {
        MetaEntity scorpian = new MetaEntity("Scorpian",
                new Components.EntitySize(GameActivity.getBlockSize() + 40,
                GameActivity.getBlockSize() + 40),
                new Components.CAndroidDrawable(R.drawable.scorpianeast),
                new Components.Movable(1/2,0),
                new Components.Position(x,y));
        return scorpian;
    }
}
