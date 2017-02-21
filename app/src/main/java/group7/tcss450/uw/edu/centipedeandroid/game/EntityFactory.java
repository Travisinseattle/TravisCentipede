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
        return new MetaEntity();
    }

    public static MetaEntity createShip() {
        MetaEntity ship = new MetaEntity();
        ship.add(new Components.CAndroidDrawable(R.drawable.alienblaster));
        ship.add(new Components.Touch());
        ship.add(new Components.Movable());
        ship.add(new Components.Position());
        ship.add(new Components.Health());
        return new MetaEntity();
    }

}
