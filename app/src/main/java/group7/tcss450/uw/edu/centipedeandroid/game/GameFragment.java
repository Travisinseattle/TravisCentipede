package group7.tcss450.uw.edu.centipedeandroid.game;


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

    private GameView mGameView;


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            return new BattleSurfaceView(getActivity());
//        }
//        return new GameView(getContext());
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        mGameView = (GameView) v.findViewById(R.id.game);
        return v;
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
//    }


}
