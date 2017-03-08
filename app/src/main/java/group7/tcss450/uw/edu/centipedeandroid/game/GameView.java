package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.LinkedList;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.EntityManager;
import group7.tcss450.uw.edu.centipedeandroid.game.system.CentMovementSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.CollisionSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.DestroySystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.DrawHitBoxSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.GameWinSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.MovementSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.PhysicsSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.RenderDamagedSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.RenderSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.ShootSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.system.TouchSystem;

/**
 * Created by Travis Holloway on 1/24/2017.
 * GameVIew class that serves as the Canvas for the game.
 */
public class GameView extends SurfaceView implements Runnable {

    /****************************************Constants*********************************************/

    public static int INITIAL_BULLET_SPEED = 300;

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
     * object to count FPS.
     */
    protected long mFps;

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
     * Bullet object for the player
     */
    protected MetaEntity mPlayerBullet;

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
    private int mScore;

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

        mOrderedSubSystems = new LinkedList<SubSystem>();
        mEntityManager = new EntityManager();
        MetaEntity.defaultEntityManager = mEntityManager;
        mRenderSystem = new RenderSystem(this);
        mRenderDamagedSystem = new RenderDamagedSystem(this);
//        myCollisions = new ArrayList<>();

        /**
         * set the value for the screen size.
         */
        mScreenSizeX = screenX;
        mScreenSizeY = screenY;
        myMoveSegement = false;
        myHitDebug = new DrawHitBoxSystem(this);
        this.mBlockSize = block;
        mMap = new Map(mScreenSizeX / mBlockSize, mScreenSizeY /mBlockSize, mBlockSize);
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

    /*****************************************Getters and Setters**********************************/

    /**
     * Getter for the bullet speed.
     * @return mBulletSpeed the speed of a bullet.
     */
    public int getBulletSpeed() {
        return mBulletSpeed;
    }

    public float getmTouchX() {
        return mTouchX;
    }

    public float getmTouchY() {
        return mTouchY;
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
        mPlayerShip = EntityFactory.createShip();

        ArrayList<Components.Position> mushroomPositions = mMap.getMushroomPositions();

        for (Components.Position p : mushroomPositions) {
            EntityFactory.createMushroom(p);
        }

        /**
         * Creates the centipede object.
         */
        mCentipede = EntityFactory.createCentipede(this, 7);

        mOrderedSubSystems.add(new TouchSystem(this));
        mOrderedSubSystems.add(new PhysicsSystem(this));
        mOrderedSubSystems.add(new CollisionSystem(this));
        mOrderedSubSystems.add(new ShootSystem(this));
        mOrderedSubSystems.add(new CentMovementSystem(this));
        mOrderedSubSystems.add(new MovementSystem(this));
        mOrderedSubSystems.add(new DestroySystem(this));
        mOrderedSubSystems.add(new GameWinSystem(this));
    }

    private void renderScore() {
        mCanvas.drawText(getResources().getString(R.string.score) + mScore, FONT_SIZE_LARGE,
                FONT_SIZE_LARGE + 10, mPaint);
//        mCanvas.drawText("Lives: 3", mScreenSizeX - FONT_SIZE_LARGE * 2, FONT_SIZE_LARGE + 10, mPaint);
    }

    /**
     * Method to draw the mGameView and display graphics
     */
    public void draw() {
        //validity check on surface area to catch crashes.
        if (mHolder.getSurface().isValid()) {
            //lock the canvas
            mCanvas = mHolder.lockCanvas();

            /**
             * If Debug is true, display statistics of the objects on screen.
             */
            if (DEBUG) {
//                int i = 1;
//                mCanvas.drawText(getContext().getString(R.string.screen_width) + mScreenSizeX,
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.screen_height) + mScreenSizeY,
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.fps) + mFps, FONT_SIZE_SMALL,
//                        10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.ship_x) + mPlayerShip.getX(),
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.ship_y) + mPlayerShip.getY(),
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.bullet_location) +
//                        mPlayerBullet.getX() + getContext().getString(R.string.comma) +
//                        mPlayerBullet.getY() + getContext().getString(R.string.para),
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.head_x) +
//                        mCentipede.getHead().getXCoord(), FONT_SIZE_SMALL,
//                        10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.head_y) +
//                        mCentipede.getHead().getYCoord(), FONT_SIZE_SMALL,
//                        10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.timer) +
//                        getElapsedTimeInSeconds(), FONT_SIZE_SMALL,
//                        10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.block_size) +
//                        mBlockSize, FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * i++), mPaint);
//                mCanvas.drawText(getContext().getString(R.string.score) + mScore,
//                        FONT_SIZE_SMALL, 10 + (FONT_SIZE_SMALL * (i + 1)), mPaint);
//            } else {
//                //Display score and amount of centipede bodies left.
//                mCanvas.drawText(getContext().getString(R.string.score) + mScore, FONT_SIZE_LARGE,
//                        FONT_SIZE_LARGE + 10, mPaint);
//                mCanvas.drawText(getContext().getString(R.string.segments) + mCentipede.getSize(),
//                        FONT_SIZE_LARGE, 10 + (FONT_SIZE_LARGE * 2), mPaint);
            }

            /**
             * If mGamestate is true, game is over and so draw the gameover message.
             */
            if (mGameState) {
                String go = getContext().getString(R.string.game_over);
                String score = getContext().getString(R.string.score) + mScore;
                String back = getContext().getString(R.string.press_back);
                mPaint.setTextSize(FONT_SIZE_LARGE);
                mPaint.setColor(Color.GREEN);
                float goWidth = mPaint.measureText(go);
                float scoreWidth = mPaint.measureText(score);
                float backWidth = mPaint.measureText(back);
                mCanvas.drawText(go, (mScreenSizeX / 2) - (goWidth / 2),
                        mScreenSizeY / 2 -(FONT_SIZE_LARGE), mPaint);
                mCanvas.drawText(score, (mScreenSizeX / 2) - (scoreWidth / 2),
                        mScreenSizeY / 2, mPaint);
                mCanvas.drawText(back, (mScreenSizeX / 2) - (backWidth / 2),
                        mScreenSizeY / 2 +(FONT_SIZE_LARGE), mPaint);
            }
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
        pause();
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

//        double previous = getCurrentTime();
//        double lag = 0.0;
//        while (true)
//        {
//            double current = getCurrentTime();
//            double elapsed = current - previous;
//            previous = current;
//            lag += elapsed;
//
//            processInput();
//
//            while (lag >= MS_PER_UPDATE)
//            {
//                update();
//                lag -= MS_PER_UPDATE;
//            }
//
//            render();
//        }
//        double t = 0.0;
//const double dt = 0.01;
//
//        double currentTime = hires_time_in_seconds();
//        double accumulator = 0.0;
//
//        while ( !quit )
//        {
//            double newTime = hires_time_in_seconds();
//            double frameTime = newTime - currentTime;
//            currentTime = newTime;
//
//            accumulator += frameTime;
//
//            while ( accumulator >= dt )
//            {
//                integrate( state, t, dt );
//                accumulator -= dt;
//                t += dt;
//            }
//
//            render( state );
//        }

        long lastLoopTime = System.currentTimeMillis();

        while (mPlaying) {
            //capture current time in milliseconds.
//            try {
//                Thread.sleep(lastLoopTime + 10 - System.currentTimeMillis());
//            } catch (Exception e) {
//                Log.e("this",e.toString());
//            }

            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();
//            lastFpsTime += delta;

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

//                mRenderSystem.drawBackground();
                        myHitDebug.processOneGameTick(delta);
                        mRenderSystem.processOneGameTick(delta);
                        mRenderDamagedSystem.processOneGameTick(delta);
                        renderScore();
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }

                }
            }
        }

    /**
     * The update method, updates the behavior of the objects in the game so that they
     * can be drawn correctly.
     */
//    public void update() {
//
///**
// * Place the touch system before the movement system.
// */
//
//
//
//        /**
//         * If there are no more centipede objects to kill, trigger game over boolean.
//         */
//        if (mCentipede.getSize() < 1) {
//            mGameState = true;
//        } else {
//            /**
//             * Update the ship.
//             */
////            mPlayerShip.update(mShipMovement, mTouchX);
//
//            /**
//             *  Update the centipede
//             */
//            if (!(getElapsedTimeInSeconds() < CENTIPEDE_DELAY)) {
//                mCentipede.update();
//            }
//
//            /**
//             * Check the status of a player bullet.  If it is active, update it's location,
//             * If it is inactive, shoot a new bullet.
//             */
//            if(mPlayerBullet.getStatus()) {
//                mPlayerBullet.update(mFps);
//                CentipedeBody temp = mCentipede.getHead();
//                while (temp != null) {
//                    if (temp.getVisible()) {
//                        if (RectF.intersects(mPlayerBullet.getRect(), temp.getRect())) {
//                            temp.setVisible(false);
//                            mPlayerBullet.setInactive();
//                            mScore = mScore + (mScreenSizeY - (int) temp.getYCoord());
//                            mCentipede.setSize();
//                        }
//                    }
//                    temp = temp.getNext();
//                }
//            } else {
//
//                /**
//                 * Poll the current position of the ship, adjust to the right to center the bullet.
//                 * and then call shoot.
//                 */
////                mPlayerBullet.shoot(mPlayerShip.getX(), mPlayerShip.getY(), 0);
//            }
//            if(mPlayerBullet.getY() < 0) {
//                mPlayerBullet.setInactive();
//            }
//        }
//    }

//    /**
//     * Getter for the current score, to be used for updating the score in the database.
//     *
//     * @return the Score.
//     */
//    public int getScore() {
//        return this.mScore;
//    }

    /*****************************************Private Methods**************************************/

}
