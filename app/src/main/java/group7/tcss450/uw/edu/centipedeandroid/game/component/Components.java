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
        public float x, y;
        public int width, height;
        public float rotationDegrees;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void setX(float newX) {
            this.x = newX;
        }

        public void setY(float newY) {
            this.y =newY;
        }

        @Override
        public String toString() {
            return "(" + super.toString() + " @ (" + x + "," + y + ") * rot." + rotationDegrees + ")";
        }
    }

    public static class Movable implements Component {
        public float dx, dy;
        /** How much to rotate per second (will be multipled by frametime/1000) */
        public float dRotationDegrees;

        public void setDx(float newDx) {
            this.dx = newDx;
        }

        public void setDy(float newDy) {
            this.dy = newDy;
        }

        @Override
        public String toString() {
            return "(" + super.toString() + " delta:" + dx + "," + dy + " * rot." + dRotationDegrees + ")";
        }
    }

    public static class Health implements Component {
        private int hitpoints;

        public void setHitpoints(int amount) {
            this.hitpoints = amount;
        }

        public int getHitpoints() {
            return hitpoints;
        }

        @Override
        public String toString() {
            return "("+super.toString()+"hitpoints: " +hitpoints+")";
        }
    }

    public static class Touch implements Component {
        @Override
        public String toString() {
            return "(" + super.toString() + " Touch)";
        }
    }

    public static class Damage implements Component {
        private int damage;

        public int getDamage() {
            return damage;
        }

        public void setDamage(int amount) {
            this.damage = amount;
        }

        @Override
        public String toString() {
            return "(" + super.toString() + " Damage: " + damage +")";
        }
    }
}
