package tcss450.uw.edu.groupproject450;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class GameActivity extends Activity {


    private static final int MIN_BLOCK_SIZE = 10 ;
    private int mWidth;
    private int mHeight;
    private static final int myBoardWidth = 10;
    private static final int myBoardHeight = 20;
    int mBlockSize;
    private final GameOverFragment gameOver = new GameOverFragment();
    TextView tv;


    /*The view object that holds controls the logic and graphics for
    * the game.*/
    GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(new MessageHandler(),
                new IntentFilter("kill"));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;
        calculateBlockSize();
        tv  = (TextView) findViewById(R.id.score);

        /*Initialize Gameview object and set the content to it.*/
        mGameView = new GameView(this, mWidth, mHeight, mBlockSize);
        setContentView(mGameView);
    }

    //Start game.
    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

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
        final BigInteger modWidth = BigInteger.valueOf(myBoardWidth);
        final BigInteger modHeight = BigInteger.valueOf(myBoardHeight);


        //Create an array using the BigInteger method divideAndRemainder.
        final BigInteger[] widthValues = width.divideAndRemainder(modWidth);
        final BigInteger[] heightValues = height.divideAndRemainder(modHeight);
        final int newWidth = widthValues[0].intValue();
        final int newHeight = heightValues[0].intValue();

        /*
         * If the newWidth is bigger than the allowed MIN_BLOCK_SIZE then examine
         * the quotient values of newWidth and newHeight.  Whichever is smaller will
         * then be used to update myBlockSize.  This insures that if the resize event
         * only stretches width or height the panel will not grow out of bounds.
         */
        if (newWidth >= MIN_BLOCK_SIZE) {
            if (newWidth < newHeight) {
                mBlockSize = newWidth;
            } else if (newHeight < newWidth) {
                mBlockSize = newHeight;
            }
        }

    }

    public void gameOverDiaglog(View view) {
        GameOver();
    }

    void GameOver() {
        gameOver.dismiss();
        mGameView.setVisibility(View.GONE);
        finish();
    }


    public class MessageHandler extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mGameView.pause();
//            tv.setText(String.valueOf(mGameView.getScore()));
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            gameOver.show(ft, "GameOver");

        }
    }
}
