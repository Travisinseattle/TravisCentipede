package group7.tcss450.uw.edu.centipedeandroid.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Travis Holloway on 1/24/2017.
 * GameVIew class that serves as the Canvas for the game.
 */
@SuppressLint("ViewConstructor")
class GameView extends SurfaceView implements Runnable {


    /****************************************Constants*********************************************/

    /**
     * Constant to represent how much time the game should wait before the centipede enters the
     * screen.
     */
    private static int CENTIPEDE_DELAY = 3;

    /**
     * Constant to determine the size of the font.
     */
    private static final float FONT_SIZE = 100;

    /*****************************************Fields***********************************************/

    /**
     * A field that holds the current size of a 'block' in the display grid.  Used to ensure
     * that all items are drawn at the same proportion regardless of screen display values.
     */
    protected int mBlockSize;

    /**
     * Canvas Object
     */
    protected Canvas mCanvas;

    /**
     * The Centipede object.
     */
    protected Centipede mCentipede;

    /**
     * Context object.
     */
    protected Context mContext;

    /**
     * object to count FPS.
     */
    protected long mFps;

    /**
     * Thread Object
     */
    protected Thread mGameThread = null;

    /**
     * Boolean representing an active game.  Set to true if game is not over, otherwise false.
     */
    protected boolean mGameState;

    /**
     * Holder Object
     */
    protected SurfaceHolder mHolder;

    /**
     * Paint object.
     */
    protected Paint mPaint;

    /**
     * Bullet object for the player
     */
    protected Bullet mPlayerBullet;

    /**
     * The playership object.
     */
    protected PlayerShip mPlayerShip;

    /**
     * boolean to track game state, running or not running
     */
    protected volatile boolean mPlaying;

    /**
     * The size of the screen in X coords.  Values will be used to perform
     * cartesian algebra for use determining object placement on screen.
     * This allows all objects to behave regardless of screen dimensions..
     */
    int mScreenSizeX;

    /**
     * The size of the screen in Y coords.
     */
    int mScreenSizeY;

    /**
     * Int Representing the Score
     */
    private int mScore;

    /**
     * intial state of mShip.
     */
    protected boolean mShipMovement = false;

    /**
     * Long that represents the time that the centipede should appear on screen.
     */
    private long mStartMilli;

    /**
     * The X coord of a touch.
     */
    protected float mTouchX;

    /**
     * The Y coord of a touch.
     */
    protected float mTouchY;

    /*****************************************Constructor******************************************/

    /**
     * The GameView Constructor.  Takes context and screen size from parent.
     *
     * @param context  The context of the parent constructing the ship.
     * @param screenX  The X dimensions of the parent activity.  Used to determine the size
     *                 of the ship.
     * @param screenY  The Y dimensions of the parent activity.  Used to determine the size
     *                 of the ship.
     */
    public GameView(Context context, int screenX, int screenY, int block) {
        //Call the parent first.
        super(context);

        //intialize mContext to the context passed to the constructor.
        this.mContext = context;

        //intitialize the holder and paint.
        mHolder = getHolder();
        mPaint = new Paint();

        /**
         * set the value for the screen size.
         */
        mScreenSizeX = screenX;
        mScreenSizeY = screenY;

        this.mBlockSize = block;

        /**
         * Initialize the level.
         */
        createLevel();

        /**
         * Set the playing boolean to true.
         */
        mPlaying = true;
        mStartMilli = System.currentTimeMillis();
    }

    /*****************************************Public Methods***************************************/

    /**
     * Method to initialize all objects and variables that will be drawn.
     */
    public void createLevel(){

        /**
         * set the score
         */
        mScore = 0;

        /**
         * Set the gamestate boolean to true.
         */
        mGameState = false;

        /**
         * Make a playership to be used in the game.
         */
        mPlayerShip = new PlayerShip(getContext(), mScreenSizeX, mScreenSizeY, mBlockSize);

        /**
         * make a player bullet.
         */
        mPlayerBullet = new Bullet(getContext(), mScreenSizeX, mScreenSizeY, mBlockSize);

        /**
         * Creates the centipede object.
         */
        mCentipede = new Centipede(getContext(), mScreenSizeX, mScreenSizeY, mBlockSize);

    }

    /**
     * Method to draw the mGameView and display graphics
     */
    public void draw() {
        //validity check on surface area to catch crashes.
        if (mHolder.getSurface().isValid()) {
            //lock the canvas
            mCanvas = mHolder.lockCanvas();

            //draw the background color.
            mCanvas.drawColor(Color.BLACK);

            //set the brush color
            mPaint.setColor(Color.WHITE);

            //set text size
            mPaint.setTextSize(45);

            //display current FPS
            boolean DEBUG = false;
            if (DEBUG) {
                mCanvas.drawText("Screen Width: " + mScreenSizeX, 40, 110, mPaint);
                mCanvas.drawText("Screen Height: " + mScreenSizeY, 40, 160, mPaint);
                mCanvas.drawText("FPS: " + mFps, 40, 60, mPaint);
                mCanvas.drawText("Ship X Cord: " + mPlayerShip.getX(), 40, 210, mPaint);
                mCanvas.drawText("Ship Y Cord: " + mPlayerShip.getY(), 40, 260, mPaint);
                mCanvas.drawText("Bullet (X, Y) Cord's: (" + mPlayerBullet.getX() + ", " +
                        mPlayerBullet.getY() + ")", 40, 310, mPaint);
                mCanvas.drawText("Head X Coord: " + mCentipede.getHead().getXCoord(),
                        40, 350, mPaint);
                mCanvas.drawText("Head Y Coord: " + mCentipede.getHead().getYCoord(),
                        40, 390, mPaint);
                mCanvas.drawText("Timer: " + getElapsedTimeInSeconds(), 40, 430, mPaint);
                mCanvas.drawText("Block Size: " + mBlockSize, 40, 490, mPaint);
                mCanvas.drawText("SCORE: " + mScore, 100, 590, mPaint);
            } else {
                mCanvas.drawText("SCORE: " + mScore, 100, 120, mPaint);
                mCanvas.drawText("Centipede Segments: " + mCentipede.getSize(), 50, 180, mPaint);
            }

            if (mGameState) {
                String go = "GAME OVER";
                String score = "Score: " + mScore;
                String back = "Press Back To Try Again";
                mPaint.setTextSize(FONT_SIZE);
                mPaint.setColor(Color.GREEN);
                float goWidth = mPaint.measureText(go);
                float scoreWidth = mPaint.measureText(score);
                float backWidth = mPaint.measureText(back);
                mCanvas.drawText(go, (mScreenSizeX / 2) - (goWidth / 2),
                        mScreenSizeY / 2 -(FONT_SIZE), mPaint);
                mCanvas.drawText(score, (mScreenSizeX / 2) - (scoreWidth / 2),
                        mScreenSizeY / 2, mPaint);
                mCanvas.drawText(back, (mScreenSizeX / 2) - (backWidth / 2),
                        mScreenSizeY / 2 +(FONT_SIZE), mPaint);
            }

            //Draw the mPlayShip.
            mCanvas.drawBitmap(mPlayerShip.getBitmap(),
                    mPlayerShip.getX() - (mPlayerShip.getLength() / 2),
                    mPlayerShip.getY() - (mPlayerShip.getHeight() / 2),
                    mPaint);

            //Draw the mCentipede
            CentipedeBody temp = mCentipede.getHead();
            while (temp != null) {
                if (temp.getVisible()) {
                    mCanvas.drawBitmap(temp.getBitmap(),
                            temp.getXCoord() - (temp.getWidth() / 2),
                            temp.getYCoord() - (temp.getHeight() / 2),
                            mPaint);
                }
                temp = temp.getNext();
            }

            //draw the mBullet
            if(mPlayerBullet.getStatus()) {
                mCanvas.drawBitmap(mPlayerBullet.getBitmap(),
                        mPlayerBullet.getX() - (mPlayerBullet.getWidth() / 2),
                        mPlayerBullet.getY() - (mPlayerBullet.getHeight() / 2),
                        mPaint);
            }

            //Draw it all to the screen
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * a method that defines what should happen when a user touches the screen.
     * Currently used to update the location of the ship and the origin of the bullets.
     *
     * @param motionEvent The motion event that called the method
     * @return  returns true by default, or false if the finger is no longer touching the
     * screen.
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        mTouchX = (int) motionEvent.getX();
        mTouchY = (int) motionEvent.getY();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            //Player finger has touched, update the location of the mShip.
            case MotionEvent.ACTION_DOWN:
                mShipMovement = true;
                break;
            //Player finger no longer touches, hault movement.
            case MotionEvent.ACTION_UP:
                mShipMovement = false;
                break;
        }
        return true;
    }

    /**
     * Pause method that stops the view from being drawn until resume() is called.
     */
    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error: ", "Joinging thread");
        }
    }

    /**
     * Resume method that resumes drawing the view.
     */
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    /**
     *  Run method, determines what should interact with the game thread. Used for animation of
     *  objects in the game.
     */
    @Override
    public void run() {

        while (mPlaying) {
            //capture current time in milliseconds.
            long mStartFrameTime = System.currentTimeMillis();

            //Call the update thread.
            update();
            //call the draw method.
            draw();

            /**
             * Counts the frame decay time length.
             * */
            long mFrameLength = System.currentTimeMillis() - mStartFrameTime;
            if(mFrameLength > 0) {
                mFps = 1000 / mFrameLength;
            }
        }

    }

    /**
     * Method that gets called when the gameover boolean flips, used to stop the game and
     * indicate that the session has ended.
     */
    private void GameOver() {
        Intent intent = new Intent("kill");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    /**
     * method to return the FPS as a function of the system time.
     *
     * @return  the FPS (system time - the start time) / 1000.0
     */
    public double getElapsedTimeInSeconds() {
        return (System.currentTimeMillis() - mStartMilli) / 1000.0;
    }

    /**
     * The update method, updates the behavior of the objects in the game so that they
     * can be drawn correctly.
     */
    public void update() {

        if (mCentipede.getSize() < 1) {
            mGameState = true;
           GameOver();
        } else {

            /**
             * Update the ship.
             */
            mPlayerShip.update(mShipMovement, mTouchX);

            /** Update the centipede */
            if (!(getElapsedTimeInSeconds() < CENTIPEDE_DELAY)) {
                mCentipede.update();
            }
            /**
             * Check the status of a player bullet.  If it is active, update it's location,
             * If it is inactive, shoot a new bullet.
             */
            if(mPlayerBullet.getStatus()) {
                mPlayerBullet.update(mFps);
                CentipedeBody temp = mCentipede.getHead();
                while (temp != null) {
                    if (temp.getVisible()) {
                        if (RectF.intersects(mPlayerBullet.getRect(), temp.getRect())) {
                            temp.setVisible(false);
                            mPlayerBullet.setInactive();
                            mScore = mScore + (mScreenSizeY - (int) temp.getYCoord());
                            mCentipede.setSize();
                        }
                    }
                    temp = temp.getNext();
                }
            } else {
                /**
                 * Pull the current position of the ship, adjust to the right to center the bullet.
                 * and then call shoot.
                 */
                mPlayerBullet.shoot(mPlayerShip.getX(), mPlayerShip.getY(), 0);
            }
            if(mPlayerBullet.getY() < 0) {
                mPlayerBullet.setInactive();
            }
        }
    }

    /**
     * Getter for the current score, to be used for updating the score in the database.
     *
     * @return the Score.
     */
    public int getScore() {
        return this.mScore;
    }

    /*****************************************Private Methods**************************************/

}
