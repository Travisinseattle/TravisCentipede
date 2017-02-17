package group7.tcss450.uw.edu.centipedeandroid.game.component;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;

/**
 * Created by nicholas on 2/16/17.
 */

public class Components  {

    public static class CAndroidDrawable implements Component {
        public int resourceID;
    }

    public static class Position implements Component {
        float x, y;
        int width, height;
        float rotationDegrees;

        @Override
        public String toString() {
            return "("+super.toString()+" @ ("+x+","+y+") * rot."+rotationDegrees+")";
        }
    }

    public static class Movable implements Component {
        public float dx, dy;
        /** How much to rotate per second (will be multipled by frametime/1000) */
        public float dRotationDegrees;

        @Override
        public String toString() {
            return "(" + super.toString() + " delta:" + dx + "," + dy + " * rot." + dRotationDegrees + ")";
        }
    }
}
