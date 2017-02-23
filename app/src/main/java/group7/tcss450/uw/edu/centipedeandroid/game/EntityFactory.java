package group7.tcss450.uw.edu.centipedeandroid.game;


import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/13/17.
 */

public class EntityFactory {

    public static MetaEntity createMushroom() { // Mushrooms will look like aliens for now.
        MetaEntity mushroom = new MetaEntity();
        mushroom.add(new Components.Health());
        mushroom.add(new Components.Position());
        mushroom.add(new Components.CAndroidDrawable(R.drawable.alienblaster));
        return mushroom;
    }

    public static MetaEntity createShip() {
        MetaEntity ship = new MetaEntity();
        ship.add(new Components.CAndroidDrawable(R.drawable.alienblaster));
        ship.add(new Components.Touch());
        ship.add(new Components.Movable());
        ship.add(new Components.Position((GameActivity.mWidth / 2),  (GameActivity.mHeight -
                GameActivity.mBlockSize)));
        ship.add(new Components.Health());
        return ship;
    }

    public static MetaEntity createBullet(float x, float y) {
        MetaEntity bullet = new MetaEntity();
        bullet.add(new Components.CAndroidDrawable(R.drawable.fireball));
        bullet.add(new Components.Movable(0, - 100));
                //GameActivity.getBlockSize()));
        bullet.add(new Components.Position(x, y));
        bullet.add(new Components.Health());
        bullet.add(new Components.Shoot());
        return bullet;
    }

}
