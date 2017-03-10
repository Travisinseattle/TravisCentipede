package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.EntityManager;
import group7.tcss450.uw.edu.centipedeandroid.game.system.CentMovementSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.CollisionSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.DestroySystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.DrawHitBoxSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.GameLoseSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.GameWinSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.MovementSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.PhysicsSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.RenderDamagedSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.RenderSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.ShootSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.TouchSystem;
import group7.tcss450.uw.edu.centipedeandroid.menu.HighScore;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

/**
 * Created by Travis Holloway on 1/24/2017.
 * GameVIew class that serves as the Canvas for the game.
 */
public class GameView extends SurfaceView implements Runnable {

    /****************************************Constants*********************************************/

    /**
     * Constant representing how tall the game view should be in terms of 'blocks'.  These blocks
     * will be consistent in appearance as they take however much space is available and devide
     * it by the amount specified in this constant.
     */
    private static final int BLOCK_COUNT_HEIGHT = 20;

    /**
     * Constant representing how wide the game view should be in terms of 'blocks'.  These blocks
     * will be consistent in appearance as they take however much space is available and devide
     * it by the amount specified in this constant.
     */
    private static final int BLOCK_COUNT_WIDTH = 10;

    /**
     * Constant representing the minimum amount of space that a block should be displayed at.
     */
    private static final int MIN_BLOCK_SIZE = 10 ;

    /****************************************Constants*********************************************/

    public static int INITIAL_BULLET_SPEED = 700;

    /**
     *
     */
    public static boolean DEBUG = true;

    /**
     * Constant to determine the size of the font.
     */
    public static final float FONT_SIZE_LARGE = 100;

    /**
     * Constant to determine the size of the font.
     */
    public static final float FONT_SIZE_SMALL = 40;

    /*****************************************Fields***********************************************/

    /**
     * A field that holds the current size of a 'block' in the display grid.  Used to ensure
     * that all items are drawn at the same proportion regardless of screen display values.
     */
    public int mBlockSize;

    private int mBulletSpeed;

    /**
     * Canvas Object
     */
    public Canvas mCanvas;

    /**
     * The Centipede object.
     */
    protected MetaEntity mCentipede;

    /**
     * Context object.
     */
    public Context mContext;

    /**
     * Boolean representing an active game.  Set to true if game is not over, otherwise false.
     */
    protected boolean mGameState;

    /**
     * Thread Object
     */
    protected Thread mGameThread = null;

    /**
     * Holder Object
     */
    public SurfaceHolder mHolder;

    /**
     * Paint object.
     */
    public Paint mPaint;

    private DrawHitBoxSystem myHitDebug;


    /**
     * The playership object.
     */
    protected MetaEntity mPlayerShip;

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
    public int mScore;

    /**
     * intial state of mShip.
     */
    public boolean mShipMovement = false;

    /**
     * Long that represents the time that the centipede should appear on screen.
     */
    private long mStartMilli;

    /**
     * The X coord of a touch.
     */
    public float mTouchX;

    /**
     * The Y coord of a touch.
     */
    public float mTouchY;

    private LinkedList<SubSystem> mOrderedSubSystems;

    public EntityManager mEntityManager;

    private RenderSystem mRenderSystem;

    private RenderDamagedSystem mRenderDamagedSystem;
//    public ArrayList<Collision> myCollisions;

    private Map mMap;
    public boolean myMoveSegement;

//    private A

    /*****************************************Constructor******************************************/

    /**
     * The GameView Constructor.  Takes context and screen size from parent.
     *
     * @param context  The context of the parent constructing the ship.
//     * @param screenX  The X dimensions of the parent activity.  Used to determine the size
//     *                 of the ship.
//     * @param screenY  The Y dimensions of the parent activity.  Used to determine the size
//     *                 of the ship.
     */
//    public GameView(Context context, int screenX, int screenY, int block) {
    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        //Call the parent first.
//        super(context);

        //intialize mContext to the context passed to the constructor.
        this.mContext = context;

        //intitialize the holder and paint.
        mHolder = getHolder();
        mPaint = new Paint();

        mOrderedSubSystems = new LinkedList<SubSystem>();
        mEntityManager = new EntityManager();
        MetaEntity.defaultEntityManager = mEntityManager;
        mRenderSystem = new RenderSystem(this);
        mRenderDamagedSystem = new RenderDamagedSystem(this);
//        myCollisions = new ArrayList<>();

        /**
//         * set the value for the screen size.
//         */
//        mScreenSizeX = getWidth();
//        mScreenSizeY = getHeight();
        myMoveSegement = false;
        myHitDebug = new DrawHitBoxSystem(this);
    }

    public void initSystems() {
        mOrderedSubSystems.add(new TouchSystem(this));
        mOrderedSubSystems.add(new PhysicsSystem(this));
        mOrderedSubSystems.add(new CollisionSystem(this));
        mOrderedSubSystems.add(new ShootSystem(this));
        mOrderedSubSystems.add(new CentMovementSystem(this));
        mOrderedSubSystems.add(new MovementSystem(this));
        mOrderedSubSystems.add(new DestroySystem(this));
        mOrderedSubSystems.add(new GameWinSystem(this));
        mOrderedSubSystems.add(new GameLoseSystem(this));


    }

    /*****************************************Getters and Setters**********************************/

    /**
     * Getter for the bullet speed.
     * @return mBulletSpeed the speed of a bullet.
     */
    public int getBulletSpeed() {
        return mBulletSpeed;
    }

    public int getmScreenSizeX() {
        return mScreenSizeX;
    }

    public int getmScreenSizeY() {
        return mScreenSizeY;
    }

    public int getmScore() {
        return mScore;
    }
    /*****************************************Public Methods***************************************/

    /**
     * Method to initialize all objects and variables that will be drawn.
     */
    public void createLevel(){
        mMap = new Map(mScreenSizeX / mBlockSize, mScreenSizeY /mBlockSize, mBlockSize);

        /**
         * set the score
         */
        mScore = 0;

        /**
         * set the bullet speed.
         */
        mBulletSpeed = INITIAL_BULLET_SPEED;

        /**
         * Set the gamestate boolean to true.
         */
        mGameState = false;

        /**
         * Make a playership to be used in the game.
         */
        mPlayerShip = EntityFactory.createShip(mScreenSizeX / 2, mScreenSizeY - mBlockSize, mBlockSize);

        ArrayList<Components.Position> mushroomPositions = mMap.getMushroomPositions();

        for (Components.Position p : mushroomPositions) {
            EntityFactory.createMushroom(p, mBlockSize);
        }

        /**
         * Creates the centipede object.
         */
        mCentipede = EntityFactory.createCentipede(this, 5);


    }

    private void renderScore() {
        mCanvas.drawText(getResources().getString(R.string.score) + mScore, FONT_SIZE_LARGE,
                FONT_SIZE_LARGE + 10, mPaint);
//        mCanvas.drawText("Lives: 3", mScreenSizeX - FONT_SIZE_LARGE * 2, FONT_SIZE_LARGE + 10, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateBlockSize();
        initSystems();
        createLevel();
        resume();
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
//        mTouchX = motionEvent.
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

    public void gameWin() {
        ((GameActivity) getContext()).onGameOver(mScore);
        ((GameActivity) getContext()).stopPlayer();
        updateScores();
        pause();
    }

    /*****************************************Private Methods**************************************/

    /**
     * Helper method to determine size of Panel.  Does modulus math on the width and height
     * of the Panel.  If the quotient of the width is less than the height, assign the Block
     * size to the width.  If the height is less, assign block size value to height.
     */
    public void calculateBlockSize() {
        mScreenSizeX = getMeasuredWidth();
        mScreenSizeY = getMeasuredHeight();
        /*
         * Capture the modulus values and panel dimensions and cast them to BigInteger,
         * which has a method to obtain quotient and remainders.
         */
        final BigInteger width = BigInteger.valueOf(mScreenSizeX);
        final BigInteger height = BigInteger.valueOf(mScreenSizeY);
        final BigInteger modWidth = BigInteger.valueOf(BLOCK_COUNT_WIDTH);
        final BigInteger modHeight = BigInteger.valueOf(BLOCK_COUNT_HEIGHT);


        //Create an array using the BigInteger method divideAndRemainder.
        final BigInteger[] widthValues = width.divideAndRemainder(modWidth);
        final BigInteger[] heightValues = height.divideAndRemainder(modHeight);
        final int newWidth = widthValues[0].intValue();
        final int newHeight = heightValues[0].intValue();

        /*
         * If the newWidth is bigger than the allowed MIN_BLOCK_SIZE then examine
         * the quotient values of newWidth and newHeight.  Whichever is smaller will
         * then be used to update myBlockSize.  This insures that if the resize event
         * only stretches width or height the view will not grow out of bounds.
         */
        if (newWidth >= MIN_BLOCK_SIZE) {
            if (newWidth < newHeight) {
                mBlockSize = newWidth;
            } else if (newHeight < newWidth) {
                mBlockSize = newHeight;
            }
        }
    }

    /**
     * Pause method that stops the view from being drawn until resume() is called.
     */
    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error: ", "Joining thread");
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
        long lastLoopTime = System.currentTimeMillis();

        while (mPlaying) {

            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

                for (SubSystem system : mOrderedSubSystems) {
                    system.processOneGameTick(delta);
                }

                synchronized(mHolder)
                {
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
                        myHitDebug.processOneGameTick(delta);
                        mRenderSystem.processOneGameTick(delta);
                        mRenderDamagedSystem.processOneGameTick(delta);
                        renderScore();
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }
                };
        }
    }

    /*****************************************Private Methods**************************************/


    /**
     * A private method to update the shared preferences with the new player Score.
     */
    private void updateScores() {
        List<HighScore> scores = MenuActivity.getHighScores(getContext(), getContext().getString(R.string.scores_list)); //Get the old list of scores.
        try {
            scores.add(new HighScore(mScore, new Date())); //Add new HighScore object to list.
        } catch (Exception e) {
            Log.e("ADD SCORES", e.getMessage() + "********************************************************************************************************************************************");
        }

        MenuActivity.saveHighScores(getContext(), getContext().getString(R.string.scores_list),
                scores); //Pass the list to the method for saving preferences.
    }

}
