package group7.tcss450.uw.edu.centipedeandroid.menu;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.PlayMusicTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private Context mContext;

    /**
     * The dimension of the screen on the Y axis.
     */
    public static int mHeight;

    /**
     * The dimension of the screen on the X axis.
     */
    public static int mWidth;


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * Capture the size of the screen as a point and then pass that to the calculateBlocks()
         * method so a suitable block size can be created for rendering purposes.
         */
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//        mWidth = size.x;
//        mHeight = size.y;
//        calculateBlockSize();
//        mPlayMusicTask = new PlayMusicTask();
//        /*Initialize Gameview object and set the content to it.*/
//        mGameView = new GameView(this, mWidth, mHeight, mBlockSize);
//        setContentView(R.layout.activity_game);
//
//        FrameLayout v = (FrameLayout) findViewById(R.id.activity_game);
//        v.addView(mGameView);
    }

}
