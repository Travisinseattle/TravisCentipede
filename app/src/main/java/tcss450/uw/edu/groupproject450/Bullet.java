package tcss450.uw.edu.groupproject450;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by Travis Holloway on 1/24/2017.
 * A class to represent a Bullet object, includes a rectf hitbox,
 * a bitmap image and AI behavior for movement and collision.
 */
class Bullet {

    /*****************************************Constants********************************************/

    /**
     * Constant that represents a bullet that fires from enemy.
     */
    private final static int DOWN = 1;

    /**
     * Constant that represents a bullet that fires from player ship.
     */
    private final static int UP = 0;

    /**
     * Constant to be used for sizing the bullets hit box.
     */
    private static final float RECT_SIZE = 1;


    /*****************************************Fields***********************************************/

    /**
     * Bitmap to provide the grafic for the ship.
     */
    private Bitmap mBitmap;

    /**
     * int to represent the heading of the bullet.
     */
    private int mHeading;

    /**
     * Height of the bullet.
     */
    private int mHeight;

    /**
     * boolean representing if the bullet should be drawn or not.
     */
    private boolean mIsActive;

    /**
     * Length of the bullet.
     */
    private int mLength;

    /**
     * Rectf Object, used for the hitbox of the Ship.
     */
    private RectF mRectF;

    /**
     * Field representing bullet speed.
     */
    private float mSpeed;

    /**
     * X coordinate of the bullet.
     */
    private float mXCoord;

    /**
     * Y coordinate of the bullet.
     */
    private float mYCoord;

    /*****************************************Constructor******************************************/

    /**
     * The constructor for a Bullet. Takes content and screen size from parent.
     *
     * @param context  The context of the parent constructing the bullet.
     * @param screenX  The X dimensions of the parent activity.  Used to determine the size
     *                 of the bullet.
     * @param screenY  The Y dimensions of the parent activity.  Used to determine the size
     *                 of the bullet.
     */
    Bullet (Context context, int screenX, int screenY, int block) {

        /**
         * set the dimensions of the bullet.
         */
        mLength = block / 2;
        mHeight = block / 2;

        /**
         * set the mIsActive boolean.
         */
        mIsActive = false;

        /**
         * Set the speed to a block size.
         */
        mSpeed = block;

        /**
         * create the bitmap and load the image.
         */
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, block / 2, block /2, false);

        /**
         * initialize the hitbox of the bullet
         */
        mRectF = new RectF();
    }

    /*****************************************Getters and Setters**********************************/

    /**
     * Getter for the bitmap of the bullet.
     *
     * @return mBitmap the bitmap of the graphic of the bullet.
     */
    Bitmap getBitmap(){
        return mBitmap;
    }

    /**
     * a getter that returns the Y coordinate of the Bullet.
     *
     * @return mYCoord the Y coordinate of the bullet.
     */
    float getImpactPointY(){
        if (mHeading == DOWN){
            return mYCoord + mHeight;
        }else{
            return mYCoord;
        }
    }

    /**
     * Setter to change the status of the bullet to inactive.
     */
    void setInactive(){
        mIsActive = false;
    }

    /**
     * Getter for the hitbox of the bullet.
     *
     * @return mRectF the hitbox of the bullet as a rectf object.
     */
    RectF getRect(){
        return mRectF;
    }

    /**
     * Get the status of the bullet
     *
     * @return mIsActive the status of the bullet.
     */
    boolean getStatus(){
        return mIsActive;
    }

    /**
     * Getter for the X coordinate of the bullet.
     *
     * @return mXCoord the X coordinate of the bullet.
     */
    float getX(){
        return mXCoord;
    }

    /**
     * Getter for the Y coordinate of the bullet.
     *
     * @return mYCoord the Y coordinate of the bullet.
     */
    float getY(){
        return mYCoord;
    }

    /**
     * Getter for the width of the bullet, used for drawing.
     *
     * @return mLength the width of the bullet as an int..
     */
    public int getWidth() {
        return this.mLength;
    }

    /**
     * Getter for the height of the bullet, used for drawing.
     *
     * @return mHeight the height of the bullet as an int.
     */
    public int getHeight() {
        return this.mHeight;
    }

    /*****************************************Public Methods***************************************/

    /**
     * The shoot method that determines first if a bullet is already active.  If it is not,
     * then the bullet starts at the location of the ship at the time the method is called.
     * If it is already active, this method will do nothing.
     *
     * @param touchX  The x coord of the touch event.
     * @param touchY  The Y coord of the touch event.
     * @param direction  Sets whether the button originates from the ship or an enemy.
     * @return true if the button is not active, false otherwise.
     */
    boolean shoot(float touchX, float touchY, int direction) {
        if (!mIsActive) {
            mXCoord = touchX;
            mYCoord = touchY;
            mHeading = direction;
            mIsActive = true;
            return true;
        }
        // Bullet already active
        return false;
    }

    /**
     * Update method for drawing the bullet.
     *
     * @param fps The counter of the program.
     */
    void update(long fps){

        /**
         * move the bullet up or down according to the heading of bullet and update the Y
         * Coordinates accordingly.
         */
        if(mHeading == UP){
            mYCoord = mYCoord - mSpeed;
        }else{
            mYCoord = mYCoord + mSpeed;
        }

        /**
         * Update the hitbox with the new location of the bullet.
         */
        mRectF.left = mXCoord;
        mRectF.right = mXCoord + RECT_SIZE;
        mRectF.top = mYCoord;
        mRectF.bottom = mYCoord + RECT_SIZE;
    }

    /*****************************************Private Methods**************************************/
}
