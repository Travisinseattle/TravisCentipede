package group7.tcss450.uw.edu.centipedeandroid.game;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import java.math.BigInteger;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.authentication.RegisterFragment;
import group7.tcss450.uw.edu.centipedeandroid.menu.GameOverFragment;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuFragment;

public class GameActivity extends AppCompatActivity implements GameOverFragment.OnFragmentInteractionListener, MenuFragment.OnStartGame {

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

    /*****************************************Fields***********************************************/

    /**
     * Field representing the size of a block in the display field, used to maintain display
     * proportions regardless of display parameters.
     */
    static int mBlockSize;

    /**
     * The view object that holds controls the logic and graphics for
     * the game.
     */
    GameView mGameView;

    /**
     * The dimension of the screen on the Y axis.
     */
    public static int mHeight;

    /**
     * The dimension of the screen on the X axis.
     */
    public static int mWidth;

    /**
     * Variable that will play music
     */
    private PlayMusicTask mPlayMusicTask;


    /*****************************************Constructor******************************************/

    /*****************************************Getters and Setters**********************************/

    /**
     * A getter to return the block size calculated for the screen dimensions.
     *
     * @return mBlockSize the value of the calculated block size
     */
    public static float getBlockSize() {
        return (float) mBlockSize;
    }

    /**
     * Getter to return the GameView of the GameActivity
     *
     * @return mGameView the GameView of the GameActivity
     */
    public GameView getGameView() {
        return this.mGameView;
    }

    /*****************************************Public Methods***************************************/

    /**
     * OnCreate method for the activity.  Calculates and instantiates the block size to be used for
     * drawing game objects.
     *
     * @param savedInstanceState The instance state passed to onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Capture the size of the screen as a point and then pass that to the calculateBlocks()
         * method so a suitable block size can be created for rendering purposes.
         */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;
        calculateBlockSize();

        /*Initialize Gameview object and set the content to it.*/
        mGameView = new GameView(this, mWidth, mHeight, mBlockSize);
        setContentView(mGameView);
//        mPlayMusicTask = new PlayMusicTask();
//        mPlayMusicTask.execute(292);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        GameOverFragment fragment = new GameOverFragment();
//        fragmentTransaction.replace(R.id.activity_game, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    /**
     * OnPause method to determine behavior when the app pauses
     */
    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
       // mPlayMusicTask.stopPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        //mPlayMusicTask.stopPlayer();
    }

    /**
     * OnResume method to determine behavior when the app resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
        //mPlayMusicTask = new PlayMusicTask();
        //mPlayMusicTask.execute(292);
    }

    /*****************************************Private Methods**************************************/

    /**
     * Helper method to determine size of Panel.  Does modulus math on the width and height
     * of the Panel.  If the quotient of the width is less than the height, assign the Block
     * size to the width.  If the height is less, assign block size value to height.
     */
    private void calculateBlockSize() {
        /*
         * Capture the modulus values and panel dimensions and cast them to BigInteger,
         * which has a method to obtain quotient and remainders.
         */
        final BigInteger width = BigInteger.valueOf(mWidth);
        final BigInteger height = BigInteger.valueOf(mHeight);
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

    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStartGame() {

    }

    @Override
    public void onPlayer() {

    }
}
