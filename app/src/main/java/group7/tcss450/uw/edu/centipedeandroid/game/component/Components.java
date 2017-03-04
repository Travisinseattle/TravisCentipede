package group7.tcss450.uw.edu.centipedeandroid.game.component;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.MetaEntity;

/**
 * Created by nicholas on 2/16/17.
 */

public class Components  {

    public static class Hazard implements Component {
        public enum Team {
            ALL,
            RED,
            BLUE,
            WHITE
        }
        public int myDamage;
        public Hazard() {
            myDamage = 0;
        }
        public Hazard(int theDamage) {
            myDamage = theDamage;
        }
    }

    public static class Team implements Component {
        public enum Color {

            Red,
            Blue,
            White
        }
        public Color myTeam;
        public Team(Color theTeam) {
            myTeam = theTeam;
        }
    }

    public interface CollisionListener {
        void onCollision(GameView v, UUID theEntity, UUID theOtherEntity);
    }

    public static class Obstacle implements Component, CollisionListener {
        public boolean mySolid;
        public Obstacle(boolean theSolid) {
            mySolid = theSolid;
        }

        @Override
        public void onCollision(GameView v, UUID theEntity, UUID theOtherEntity) {
            if (mySolid) {


            }

        }
    }

    public static class Collision implements Component {
        public UUID collidedWith;
        public Collision() {
            collidedWith = null;
        }
        public Collision(UUID theCollision) {
            collidedWith = theCollision;
        }
    }

//    public static class Collided implements Component {
//        public UUID
//    }

    public static class Direction implements Component {

        private boolean mDir;

        public Direction(boolean theDir) {
            mDir = theDir;
        }

        public boolean getDir() {
            return mDir;
        }

        public void swapDir(boolean theDir) {
            mDir = !theDir;
        }

    }
    public static class CentipedeID implements Component {
        private UUID[] mIDs;

        private UUID mHead;

        public CentipedeID(UUID[] theIDs) {
            mIDs = theIDs;
            mHead = mIDs[0];
        }
    }

    public void splitCentipede(UUID[] theIDS, UUID theSeg) {
        int head = 0;
        int tail = 0;
        ArrayList<MetaEntity> newCentipedes = new ArrayList<>();
        for (int i = 0; i<theIDS.length; i++) {
            if (theIDS[i] == theSeg) {
                head = (i - 1);
                tail = (i + 1);
            }
        }
        UUID[] leftCent = new UUID[head];
        UUID[] rightCent = new UUID[tail];
        for (int j = 0; j < head; j++) {
            leftCent[j] = theIDS[j];
        }

        for (int k = tail; k < theIDS.length; k++) {
            rightCent[k] = theIDS[k];
        }
        EntityFactory.createCentipede(leftCent);
        EntityFactory.createCentipede(rightCent);
    }

    public static class ParentComponent implements Component {
        public UUID[] mySegments;

        public ParentComponent (UUID[] theSegmentIDs) {
            mySegments = theSegmentIDs;
        }
    }

    public static class SegmentComponent implements Component {
        public UUID myParentEntity;

        public SegmentComponent(UUID theParentEntity) {
            myParentEntity = theParentEntity;
        }
    }

    public static class CAndroidDrawable implements Component {
        private int myResourceID;

        public CAndroidDrawable(int theResourceID) {
            myResourceID = theResourceID;
        }

        public int getMyResourceID() {
            return myResourceID;
        }
    }

    public static class DamagedDrawable implements Component {
        // 0 index is no damage
        public int[] myResourceID;
        public DamagedDrawable(int[] theResourceID) {
            myResourceID = theResourceID;
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
        private final int myMaxHitPoints;
        private int myHitPoints;

        public Health(final int theHitPoints) {
            myMaxHitPoints = theHitPoints;
            myHitPoints = theHitPoints;

        }

        public void setHitPoints(final int amount) {
            myHitPoints = amount;
        }

        public int getMyMaxHitPoints() {
            return myMaxHitPoints;
        }
        public int getHitPoints() {
            return myHitPoints;
        }

        @Override
        public String toString() {
            return "("+super.toString()+"hitPoints: " + myHitPoints +")";
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

        public Damage() {
            damage = 0;
        }

        public Damage(int value) {
            damage = value;
        }

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

        public boolean mySolid = true;

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
