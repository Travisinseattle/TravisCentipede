package group7.tcss450.uw.edu.centipedeandroid.game;


import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 */

public class EntityFactory {

    public static MetaEntity createMushroom(GameView theGameView) { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        mushroom.add(new Components.EntitySize(GameActivity.getBlockSize(),
                GameActivity.getBlockSize()));
        mushroom.add(new Components.Health());
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(theGameView, R.drawable.shroom));
        return mushroom;
    }

    public static MetaEntity createShip(GameView theGameView) {
        MetaEntity ship = new MetaEntity();
        ship.add(new Components.EntitySize(GameActivity.getBlockSize() * 2,
                GameActivity.getBlockSize()));
        ship.add(new Components.CAndroidDrawable(theGameView, R.drawable.alienblaster));
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
        bullet.add(new Components.CAndroidDrawable(theGameView, R.drawable.fireball));
        bullet.add(new Components.Movable(0, - 100));
                //GameActivity.getBlockSize()));
        bullet.add(new Components.Position(x, y));
        bullet.add(new Components.Health());
        bullet.add(new Components.Shoot());
        return bullet;
    }

}
