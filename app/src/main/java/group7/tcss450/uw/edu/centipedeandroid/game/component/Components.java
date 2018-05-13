package group7.tcss450.uw.edu.centipedeandroid.game.component;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.MetaEntity;

/**
 * Class that has multiple compents that do different things such as movement and
 * hit boxes and are attached to entities.
 *
 * Created by nicholas on 2/16/17.
 */
public class Components  {

    public static class Score implements Component {
        public int myScore;

        public Score(int theScore) {
            myScore = theScore;
        }
    }

    /**
     * Container class component.
     */
    public static class Container implements Component {

        /** A Rectf hitbox of the space */
        public RectF mySpace;

        /**
         * Constructor for the container class
         *
         * @param theSpace
         */
        public Container(RectF theSpace) {
            mySpace = theSpace;
        }
    }

    /**
     * Contained class component for containing UUID
     */
    public static class Contained implements Component {
        /** the container to gold the ID's */
        public UUID myContainer;

        /**
         * Constructor for the container component.
         *
         * @param theContainer
         */
        public Contained(UUID theContainer) {
            myContainer = theContainer;
        }
    }

    /**
     * Hazard class component for containing UUID
     */
    public static class Hazard implements Component {

        /** An enum set of team colors */
        public EnumSet<Team.Color> myAffected;

        /** Tracked for damage */
        public int myDamage;

        /**
         * Constructor that sets the color and damage.
         */
        public Hazard() {
            myDamage = 0;
            myAffected = EnumSet.noneOf(Team.Color.class);
        }

        /**
         * Constructor that takes a damage variable.
         *
         * @param theDamage of the object.
         */
        public Hazard(int theDamage) {
            myDamage = theDamage;
            myAffected = EnumSet.noneOf(Team.Color.class);
        }
        
        /**
         * Constructor that takes a damage variable and the colors of the teams.
         *
         * @param theDamage of the object.
         * @param theTeam is the colors of the team.
         */
        public Hazard(int theDamage, Team.Color... theTeam) {
            myDamage = theDamage;
            myAffected = EnumSet.copyOf(Arrays.asList(theTeam));
        }
    }

    /**
     * Class component that assigns a team for the entity.
     */
    public static class Team implements Component {
        /**
         * Enums of the colors for teams.
         */
        public enum Color {
            Red,
            Blue
        }

        /** The color of the team */
        public Color myTeam;

        /**
         * Constructor that takes a color and sets it as the team color.
         *
         * @param theTeam a color.
         */
        public Team(Color theTeam) {
            myTeam = theTeam;
        }
    }

    /**
     * Interface for collision.
     */
    public interface CollisionListener {
        void onCollision(GameView v, UUID theEntity, UUID theOtherEntity);
    }

    /**
     * Obstacle component that helps with collision.
     */
    public static class Obstacle implements Component, CollisionListener {

        /** boolean to check if is solid */
        public boolean mySolid;

        /**
         * Constructor for obstacle class
         *
         * @param theSolid
         */
        public Obstacle(boolean theSolid) {
            mySolid = theSolid;
        }

        /**
         * Method that checks for collison
         *
         * @param v
         * @param theEntity
         * @param theOtherEntity
         */
        @Override
        public void onCollision(GameView v, UUID theEntity, UUID theOtherEntity) {
            if (mySolid) {


            }

        }
    }

    /**
     * Collsion component that will make the entity a collision
     * target by the corresponding system.
     */
    public static class Collision implements Component {

        /** the colliding width */
        public UUID collidedWith;

        /**
         *  Emtpy Collision constructor
         */
        public Collision() {
            collidedWith = null;
        }

        /** collision constructor that takes a UUID
         *
         * @param theCollision
         */
        public Collision(UUID theCollision) {
            collidedWith = theCollision;
        }
    }

    /**
     * Direction class component that controls the direction of the centipede segments.
     */
    public static class Direction implements Component {

        /** boolean that controls whether the segment goes left or right. */
        private boolean mDir;
        public boolean collided = false;

        /** Direction constructor that sets the current direction
         *
         * @param theDir to move the segment.
         */
        public Direction(boolean theDir) {
            mDir = theDir;
        }

        /**
         *  Getter method that returns the current direction
         *
         * @return a boolean of the direction.
         */
        public boolean getDir() {
            return mDir;
        }

        public void setDir(boolean theDir) {
            mDir = theDir;
        }

    }

    /**
     * CentipedeID class component which keeps track of the centipede segments that their
     * corresponding head node.
     */
    public static class CentipedeID implements Component {
        public UUID[] myIDs;

        UUID myHead;

        /** Constructor for that intializes fields
         *
         * @param theIDs for this centipede.
         * @param theSize
         */
        public CentipedeID(UUID[] theIDs) {
            myIDs = theIDs;
            myHead = myIDs[0];
        }

        public int getSize() {
            return myIDs.length;
        }

    }

    /**
     * Class component that sets the parent commponent of the list of UUID's
     */
    public static class ParentComponent implements Component {

        /** The list of segments */
        public UUID[] mySegments;

        /** Constructor for class that sets the fields
         *
         * @param theSegmentIDs ID's whose this is their parent component.
         */
        public ParentComponent (UUID[] theSegmentIDs) {
            mySegments = theSegmentIDs;
        }
    }

    /**
     * Class component determines the Dx/Dy for a segment.
     */
    public static class SegmentMovable implements Component {
        public float dx;
        public float dy;
    }

    /**
     * Class component that assigns a centipede segment its parent entity
     */
    public static class SegmentComponent implements Component {

        /** The parent entity */
        public UUID myParentEntity;

        /**
         * Constructor that sets the parent entity.
         *
         * @param theParentEntity the parent entity of the segment.
         */
        public SegmentComponent(UUID theParentEntity) {
            myParentEntity = theParentEntity;
        }
    }

    /**
     * Class component that sets the bitmap for all of the entities.
     */
    public static class CAndroidDrawable implements Component {

        /** The resource id for finding the drawable. */
        private int myResourceID;

        /**
         * Constructor that takes in a resource id and assigns it to
         * the field.
         *
         * @param theResourceID a resource id for the bitmap.
         */
        public CAndroidDrawable(int theResourceID) {
            myResourceID = theResourceID;
        }

        /**
         * Setter method for the resource id
         */
        public void setDrawable(int theResourceID) {
            myResourceID = theResourceID;
        }

        /**
         * Getter method for the resource id
         *
         * @return an integer of the resource id.
         */
        public int getMyResourceID() {
            return myResourceID;
        }

    }

    /**
     * Class component that draws bitmaps for damaged components such as the
     * mushroom.
     */
    public static class DamagedDrawable implements Component {
        // 0 index is no damage
        /** The array of id's */
        public int[] myResourceID;

        /**
         * Constructor for DamageDrawable, puts all of the ID's into the correct array
         *
         * @param theResourceID
         */
        public DamagedDrawable(int[] theResourceID) {
            myResourceID = theResourceID;
        }
    }

    /**
     * Position component that assigns where on the screen an entity is.
     */
    public static class Position implements Component {
        /** The x and y positions */
        public float x, y;

        /** The width and height. */
        public int width, height;

        /** the rotation degree of the component */
        public float rotationDegrees;

        /**
         * Empty constructor for position
         */
        public Position() {}

        /**
         *Constructor that takes an x and y value and places the components position at
         * those coordinates.
         *
         * @param x value of the pos
         * @param y value of the pos
         */
        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Getter method that returns the x position.
         *
         * @return a float x pos
         */
        public float getX() {
            return x;
        }

        /**
         * Getter method that returns the y position.
         *
         * @return a float y pos
         */
        public float getY() {
            return y;
        }

        /** Method that sets the x pos of the entity
         *
         * @param newX the new x pos
         */
        public void setX(float newX) {
            this.x = newX;
        }

        /**
         * Method that sets the y pos
         *
         * @param newY the new y pos
         */
        public void setY(float newY) {
            this.y =newY;
        }

        /** Method that returns the string representation of the component.
         *
         * @return a string
         */
        @Override
        public String toString() {
            return "(" + super.toString() + " @ (" + x + "," + y + ") * rot." + rotationDegrees + ")";
        }
    }

    /**
     * Movable component that controls the speed at which a entity moves on the screen.
     */
    public static class Movable implements Component {
        /** The x and y speed of the entity */
        public float dx, dy;

        /**
         * Empty Constructor
         */
        public Movable() {}

        /**
         * Constructor that takes a x speed and y speed.
         *
         * @param x the x movable speed.
         * @param y the y movable speed.
         */
        public Movable(float x, float y) {
            this.dx = x;
            this.dy = y;
        }
        /** How much to rotate per second (will be multipled by frametime/1000) */
        public float dRotationDegrees;

        /**
         * Method that sets the x speed for the movable.
         *
         * @param newDx the new x speed.
         */
        public void setDx(float newDx) {
            this.dx = newDx;
        }

        /** Setter method that sets the y speed.
         *
         * @param newDy the new y speed of the entity.
         */
        public void setDy(float newDy) {
            this.dy = newDy;
        }

        /**
         * Getter method that returns the current x speed.
         *
         * @return a float of the x speed.
         */
        public float getDx() {
            return this.dx;
        }

        /**
         * Getter method that returns the current speed on the y axis.
         *
         * @return a float of the current y speed.
         */
        public float getDy() {
            return this.dy;
        }

        /**
         * Method that returns a string representation of the component.
         *
         * @return a string.
         */
        @Override
        public String toString() {
            return "(" + super.toString() + " delta:" + dx + "," + dy + " * rot." + dRotationDegrees + ")";
        }
    }

    /**
     * Health Component that sets the health points for the entity.
     */
    public static class Health implements Component {
        /** The maximum hitpoints */
        private final int myMaxHitPoints;

        /** The hitpoints of the entity */
        private int myHitPoints;

        /**
         * Constructor for the healt component that sets the entities
         * health points
         *
         * @param theHitPoints is the health of the entity.
         */
        public Health(final int theHitPoints) {
            myMaxHitPoints = theHitPoints;
            myHitPoints = theHitPoints;

        }

        /**
         * Setter method that sets the health points for the entity.
         *
         * @param amount of health points.
         */
        public void setHitPoints(final int amount) {
            myHitPoints = amount;
        }

        /**
         * Getter method that returns the maximum number of health points.
         *
         * @return an int of the max health points.
         */
        public int getMyMaxHitPoints() {
            return myMaxHitPoints;
        }

        /**
         * Getter method that returns the current hit points of the entity.
         *
         * @return an integer of the health points.
         */
        public int getHitPoints() {
            return myHitPoints;
        }

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return "("+super.toString()+"hitPoints: " + myHitPoints +")";
        }
    }

    /**
     * Touch component for touch movement of the ship.
     */
    public static class Touch implements Component {

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return "(" + super.toString() + " Touch)";
        }
    }

    /**
     * Damage component that controls the damage of the entities such as setting
     * damage and returning the damage.
     */
    public static class Damage implements Component {

        /** The damage */
        private int damage;

        /**
         * Empty constructor for the class.
         */
        public Damage() {
            damage = 0;
        }

        /**
         * Constructor that takes an integer value for damage.
         *
         * @param value the amount of damage being done.
         */
        public Damage(int value) {
            damage = value;
        }

        /**
         * Getter method that returns the damage.
         *
         * @return an int of the damage.
         */
        public int getDamage() {
            return damage;
        }

        /**
         * Setter method that sets the current damage.
         *
         * @param amount the amount of damage to be changed to.
         */
        public void setDamage(int amount) {
            this.damage = amount;
        }

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return "(" + super.toString() + " Damage: " + damage +")";
        }
    }

    /**
     * Component that identifies the bullet entity.
     */
    public static class Shoot implements Component {

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return super.toString();
        }
    }

    /**
     * Component class that creates a hitbox for the entity using rectf.
     */
    public static class HitBox extends RectF implements Component {
        /** Boolean that checks if is solid */
        public boolean mySolid = true;

        /** Rectf used for the hitbox bounds */
        RectF hitBox;

        /**
         * Constructor for hitbox that creates a new hitbox
         */
        public HitBox() {
            this.hitBox = new RectF();
        }

        /**
         * Constructor that takes left, top, right, and bottom bounds
         * to create an accurate hitbox.
         *
         * @param left the left bounds.
         * @param top the top bounds.
         * @param right the right bounds.
         * @param bottom the bottom bounds.
         */
        public HitBox(final float left, final float top,
                      final float right, final float bottom) {
            this.hitBox = new RectF(left, top, right, bottom);
        }

        /**
         * Getter method that returns the
         * Rectf hitbox
         *
         * @return a Rectf object.
         */
        public RectF getHitBox() {
            return this.hitBox;
        }

        /**
         * Setter method that sets the bounds for the hitbox by passing an already
         * existing hitbox.
         *
         * @param hitBox a Rectf hitbox.
         */
        public void setHitBox(final RectF hitBox) {
            this.hitBox = hitBox;
        }

        /**
         * Setter method that takes specific bounds and applies them to the hitbox.
         * @param left the left bounds
         * @param top the top bounds
         * @param right the right bounds
         * @param bottom the bottom bounds
         */
        public void setHitBox(final float left, final float top,
                              final float right, final float bottom) {
            this.hitBox.set(left, top, right, bottom);
        }

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return super.toString() + "HitBox Coord's: (left: " + hitBox.left + ", top: " +
                    hitBox.top + ", right: " + hitBox.right + ", bottom: " + hitBox.bottom + ").";
        }
    }

    /**
     * Component class that sets the size of the entity.
     */
    public static class EntitySize implements Component {

        /** Width of the entity */
        private float entityWidth;

        /** Height of the entity */
        private float entityHeight;

        /** Half the width, for scaling */
        private float halfWidth;

        /** Half the height, for scaling */
        private float halfHeight;

        /**
         * Constructor that sets the fields to 0
         */
        public EntitySize() {
            this.entityWidth = 0;
            this.entityHeight = 0;
            this.halfWidth = 0;
            this.halfHeight = 0;
        }

        /**
         * Constructor that takes a width and heigh for the entity.
         *
         * @param width of the entity.
         * @param height of the entity.
         */
        public EntitySize(float width, float height) {
            this.entityWidth = width;
            this.entityHeight = height;
            this.halfWidth = width / 2;
            this.halfHeight = height / 2;
        }

        /**
         * Getter method that returns the width of the entity.
         *
         * @return a float width.
         */
        public float getEntityWidth() {
            return entityWidth;
        }

        /**
         * Getter method that returns the height of the entity.
         *
         * @return a float height.
         */
        public float getEntityHeight() {
            return entityHeight;
        }

        /**
         * Getter method that returns half the width of the entity.
         *
         * @return a float of half the width.
         */
        public float getHalfWidth() {
            return halfWidth;
        }

        /**
         * Getter method that returns half the height of the entity.
         *
         * @return a float of half the height.
         */
        public float getHalfHeight() {
            return halfHeight;
        }

        /**
         * String method that returns a string representation of the class.
         *
         * @return a String.
         */
        @Override
        public String toString() {
            return super.toString() + "Entity Size: (Width: " + entityWidth + ", Height: " +
                    entityHeight + ").";
        }
    }
}
