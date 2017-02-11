package group7.tcss450.uw.edu.centipedeandroid.game;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * CentipedeBody class that acts as a node class that tracks the next node and also keeps track of
 * the nodes position on the screen.
 *
 * Created by addison on 1/24/2017.
 */

public class CentipedeBody {

    /** Y boundary for the application. */
    private final static int MIN_BOUNDARY = 0;

    /** Divisor for the height of the body. */
    private final static int HEIGHT_DIVISOR = 19;

    /** Width divisor for the body of the centipede. */
    private final static int WIDTH_DIVISOR = 8;

    /** Width of the border. */
    private final static int WIDTH_BORDER = 60;

    /** Bitmap of the centipedebody*/
    private Bitmap mCentiBody;

    /** X coordinate of the node */
    private float mXCoord;

    /** y coordinate of the node */
    private float mYCoord;

    /** Boolean that controls direction of node. */
    private boolean mEast;

    /** Boolean that controls direction of the node. */
    private boolean mWest;

    /** Hitbox for the node. */
    private RectF mRectf;

    /** Next node connected to current node. */
    private CentipedeBody mNext;

//    /** Prev node of current node. */   not implemented yet.
//    private CentipedeBody mPrev;

    /** Size of y axis for screen. */
    private int mScreenY;

    /** Size of x axis for screen. */
    private int mScreenX;

    /** Height of the centipede body */
    private int mHeight;

    /** Width of the centipede body */
    private int mWidth;

    /** Boolean controls if centipedebody is visible */
    private boolean isVisible;

    /** Bitmap for the centipede head */
    private Bitmap mCentipedeHead;

    /** Speed of the centipede */
    private int mSpeed;

    /**
     * Constructor for empty node.
     */
    public CentipedeBody() {
        isVisible  = true;
    }

    /**
     * Constructor for CentipedeBody. Initializes variable.
     *
     * @param bitImage  The bitimage used for the node.
     * @param xPos  The X position of the node.
     * @param yPos  The Y position of the node.
     * @param screenX  The X dimensions of the parent activity.
     * @param screenY  The Y dimensions of the parent activity.
     * @param block is the block sie of the centipedebody.
     */
    public CentipedeBody(Bitmap bitImage, int xPos, int yPos, int screenX, int screenY, int block) {
        mCentiBody = bitImage;
        mXCoord = xPos;
        mYCoord = yPos;
        mScreenX = screenX;
        mScreenY = screenY;
        mNext = null;
        mWidth = block;
        mHeight = block;
        //mPrev = null;
        mEast = true;
        mWest = false;
        mCentiBody = Bitmap.createScaledBitmap(mCentiBody, mWidth, mHeight, false);
        isVisible = true;
        mRectf = new RectF();
    }

    /**
     * Setter that sets the bitmap for the centipedebody.
     * @param bitmap the bitmap image used for the centipedebody.
     */
    public void setBitmap(Bitmap bitmap) {
        mCentiBody = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false);
    }

    /**
     * Getter that gets the height of the centipedebody.
     * @return an int that is the height of the body.
     */
    public int getHeight() {
        return this.mHeight;
    }

    /**
     * Getter that returns the width of the centipedebody.
     * @return an int that is the width of the body.
     */
    public int getWidth() {
        return this.mWidth;
    }

    /**
     * Getter that returns the bitmap currently being used by the node.
     *
     * @return a bitmap of the centipede body.
     */
    public Bitmap getBitmap() {
        return mCentiBody;
    }

    /**
     * Getter that returns the x position of the node.
     *
     * @return a float of the current x coordinate
     */
    public float getXCoord() {
        return mXCoord;
    }

    /**
     * Getter that returns the y position of the node.
     *
     * @return a float of the current y coordinate.
     */
    public float getYCoord() {
        return mYCoord;
    }

//    public CentipedeBody getPrev() {
//        return mPrev;
//    }
//
//    public void setPrev(CentipedeBody prev) {
//        mPrev = prev;
//    }

    /**
     * Getter that returns the the next node.
     *
     * @return a CentipedeBody node.
     */
    public CentipedeBody getNext() {
        return mNext;
    }

    /**
     * Getter method that returns whether or not the body is
     * visible.
     *
     * @return a boolean true or false.
     */
    public boolean getVisible() {
        return isVisible;
    }

    /**
     * Getter method that returns whether or not the body is
     * visible.
     *
     * @return a boolean true or false.
     */
    public void setVisible(boolean isVis) {
        isVisible = isVis;
    }

    /**
     * Setter that sets the next node.
     *
     * @param next the next node for the current node to point to.
     */
    public void setNext(CentipedeBody next) {
        mNext = next;
    }

    /**
     * Method that moves a centipedebody south.
     *
     * @param yPos the current y pos of the node.
     */
    private void moveDown(float yPos) {
        if (yPos < 0) {
            mYCoord += 5;
        } else {
            mYCoord += mHeight;
        }
    }

    /**
     * Method that moves the centipede body right.
     */
    private void moveRight() {
        mXCoord += 5;
    }

    /**
     * Method that moves the centipede body left.
     */
    private void moveLeft() {
        mXCoord -= 5;
    }

    /**
     * Method that sets the speed of the centipede.
     *
     * @param newSpeed is an integer speed of the body.
     */
    private void setSpeed(int newSpeed) {
        mSpeed = newSpeed;
    }

    /**
     * Method that sets the direction west for movement.
     *
     * @param west  a boolean true or false if the body moves west.
     */
    public void setWest(boolean west) {
        this.mWest = west;
    }

    /**
     * Method that sets the direction east for movement.
     *
     * @param east  a boolean true or false if the body moves east.
     */
    public void setEast(boolean east) {
        this.mEast = east;
    }


    /**
     * Getter for the hitbox.
     *
     * @return mRectF The hitbox of the Ship.
     *
     */
    RectF getRect(){
        return mRectf;
    }

    /**
     * Update method that controls the centipedebody movement. Uses booleans to control
     * whether or not the centipede is traveling east or west.
     */
    public void update() {
//        if (!this.getNext().getVisible() && this.getNext() != null) {
//            setBitmap(mCentipedeHead);
//        }
        if (mYCoord < MIN_BOUNDARY) {
            moveDown(mYCoord);
        } else {
            if (mEast == true) {
                if (mXCoord >= (mScreenX-mWidth)) {
                    moveDown(mYCoord);
                    mEast = false;
                } else {
                    moveRight();
                }
            } else {
                if (mXCoord <= (MIN_BOUNDARY-mWidth/3)) {
                    moveDown(mYCoord);
                    mEast = true;
                }
                moveLeft();
            }
        }

        mRectf.left = mXCoord;
        mRectf.right = mXCoord + mWidth;
        mRectf.top = mYCoord;
        mRectf.bottom = mYCoord + mHeight;
    }


}
