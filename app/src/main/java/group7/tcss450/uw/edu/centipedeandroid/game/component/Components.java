package group7.tcss450.uw.edu.centipedeandroid.game.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;

/**
 * Created by nicholas on 2/16/17.
 */

public class Components  {

    public static class CAndroidDrawable implements Component {
        private int resourceID;

        public CAndroidDrawable(int resourceID) {
            this.resourceID = resourceID;
        }

        public int getResourceID() {
            return resourceID;
        }
    }

    public static class DamagedDrawable implements Component {
        // 0 index is no damage
        public int[] resourceID;
        public DamagedDrawable(int[] resourceID) {
            this.resourceID = resourceID;
        }
    }

    public static class Position implements Component {
        public float x, y;
        public int width, height;
        public float rotationDegrees;

        public Position() {}
        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

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

        public Movable() {}
        public Movable(float x, float y) {
            this.dx = x;
            this.dy = y;
        }
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
        private int hitPoints;

        public Health() {
            this.hitPoints = 1;
        }

        public void setHitPoints(int amount) {
            this.hitPoints = amount;
        }

        public int getHitPoints() {
            return this.hitPoints;
        }

        @Override
        public String toString() {
            return "("+super.toString()+"hitPoints: " + hitPoints +")";
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

    public static class Shoot implements Component {

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static class HitBox implements Component {

        RectF hitBox;

        public HitBox() {
            this.hitBox = new RectF();
        }

        public HitBox(final float left, final float top,
                      final float right, final float bottom) {
            this.hitBox = new RectF(left, top, right, bottom);
        }

        public RectF getHitBox() {
            return this.hitBox;
        }

        public void setHitBox(final RectF hitBox) {
            this.hitBox = hitBox;
        }

        public void setHitBox(final float left, final float top,
                              final float right, final float bottom) {
            this.hitBox.set(left, top, right, bottom);
        }

        @Override
        public String toString() {
            return super.toString() + "HitBox Coord's: (left: " + hitBox.left + ", top: " +
                    hitBox.top + ", right: " + hitBox.right + ", bottom: " + hitBox.bottom + ").";
        }
    }

    public static class EntitySize implements Component {
        private float entityWidth;
        private float entityHeight;
        private float halfWidth;
        private float halfHeight;

        public EntitySize() {
            this.entityWidth = 0;
            this.entityHeight = 0;
            this.halfWidth = 0;
            this.halfHeight = 0;
        }

        public EntitySize(float width, float height) {
            this.entityWidth = width;
            this.entityHeight = height;
            this.halfWidth = width / 2;
            this.halfHeight = height / 2;
        }

        public float getEntityWidth() {
            return entityWidth;
        }

        public float getEntityHeight() {
            return entityHeight;
        }

        public float getHalfWidth() {
            return halfWidth;
        }

        public float getHalfHeight() {
            return halfHeight;
        }

        @Override
        public String toString() {
            return super.toString() + "Entity Size: (Width: " + entityWidth + ", Height: " +
                    entityHeight + ").";
        }
    }
}
