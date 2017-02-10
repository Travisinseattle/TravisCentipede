package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import group7.tcss450.uw.edu.centipedeandroid.R;
/**
 * Created by Travis Holloway on 1/24/2017.
 * PlayerShip Class that animates and tracks collision for the player ship.
 */
class PlayerShip {

    /*****************************************Constants********************************************/

    /**
     * Constant for the starting Y position of the Ship.
     */
    private final static int INITIAL_Y = 200;

    /*****************************************Fields***********************************************/

    /**
     * Bitmap to provide the grafic for the ship.
     */
    private Bitmap mBitmap;

    /**
     * Height of the Ship.
     */
    private int mHeight;

    /**
     * Length of the ship.
     */
    private int mLength;

    /**
     * Rectf Object, used for the hitbox of the Ship.
     */
    private RectF mRectf;

    /**
     * X coordinate of the Ship.
     */
    private float mXCoord;

    /**
     * Y coordinate of the Ship.
     */
    private float mYCoord;

    /*****************************************Constructor******************************************/

    /**
     * Constructor for a PlayerShip.  Takes the context and screen size from it's parent.
     *
     * @param context  The context of the parent constructing the ship.
     * @param screenX  The X dimensions of the parent activity.  Used to determine the size
     *                 of the ship.
     * @param screenY  The Y dimensions of the parent activity.  Used to determine the size
     *                 of the ship.
     */
    PlayerShip(Context context, int screenX, int screenY, int block) {
        /**
         * initialize the hitbox
         */
        mRectf = new RectF();

        /**
         * initialize X and Y and divide to reach an appropriate size.
         */
        mLength = block * 2;
        mHeight = block;

        /**
         * Set ship initial position.
         */
        mXCoord = (screenX / 2) - (mLength / 2);
        mYCoord = screenY - INITIAL_Y;

        /**
         * Load the image for the ship
         */
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.alienblaster);

        /**
         * rescale the image
         */
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mLength, mHeight, false);
    }

    /*****************************************Getters and Setters**********************************/

    /**
     * Getter for the bitmap of the Ship.
     *
     * @return mBitmap the bitmap of the graphic of the ship.
     */
    Bitmap getBitmap(){
        return mBitmap;
    }

    /**
     * Getter for the length of the Ship.
     *
     * @return mLength the length of the ship in dp.
     */
    int getLength(){
        return this.mLength;
    }

    /**
     * Getter for the length of the Ship.
     *
     * @return mLength the length of the ship in dp.
     */
    int getHeight() {
        return this.mHeight;
    }

//    /**
//     * Getter for the hitbox.
//     *
//     * @return mRectF The hitbox of the Ship.
//     *
//     */
//    RectF getRect(){
//        return mRectf;
//    }

    /**
     * Getter for the X coordinate of the ship.
     *
     * @return mXCoord the X coordinate of the ship.
     */
     float getX(){
        return mXCoord;
    }

    /**
     * Getter for the Y coordinate of the ship.
     *
     * @return mYCoord the Y coordinate of the ship.
     */
     float getY(){
        return mYCoord;
    }

    /*****************************************Public Methods***************************************/

    /**
     * Update method. Used to update where the ship is so it can be drawn properly.
     *
     * @param isMoving Boolean that determins if this is moving or not.
     * @param theX the X coordinate of the player touch passed from GameView.java
//     * @param theY the Y coordinate of the player touch passed from GameView.java
     */
    void update(boolean isMoving, float theX) {//, float theY) {

        /**
         * First update the new X and Y coordinates of the ship.
         */
        if(isMoving) {
            mXCoord = theX;
            /**
             * Only allow the mShip to be in the bottom third of the screen.
             */
//            if (theY > (mScreenY - (mScreenY / 3))) {
//                mYCoord = theY - (mHeight / 2);
//            }
        }

        /**
         * Update the location of the hitbox as well using the new X and Y coordinates.
         */
        mRectf.top = mYCoord;
        mRectf.bottom = mYCoord + mHeight;
        mRectf.left = mXCoord;
        mRectf.right = mXCoord + mLength;

    }

    /*****************************************Private Methods**************************************/
}
