package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameOverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener {

    private MenuFragment.OnStartGame mListener;

    public GameOverFragment() {
        // Required empty public constructor
    }


    public static GameOverFragment newInstance(int theScore) {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putInt("Score", theScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        //Button b = (Button) v.findViewById(R.id.playAgain);
//        b.setOnClickListener(this);
//        b = (Button) v.findViewById(R.id.mainMenu);
//        b.setOnClickListener(this);
        Bundle args = getArguments();
        int score = args.getInt("Score", 0);
//        TextView textView = (TextView) v.findViewById(R.id.score);
//        textView.setText("Score " + score);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuFragment.OnStartGame) {
            mListener = (MenuFragment.OnStartGame) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId())
//        {
//            case R.id.playAgain:
//                mListener.onStartGame();
//                break;
//            case R.id.mainMenu:
//                MenuFragment menuFrag = new MenuFragment();
//                this.getFragmentManager().beginTransaction()
//                       .replace(R.id.activity_menu, menuFrag, "Menu Fragment")
//                        .addToBackStack(null)
//                       .commit();
//                break;
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
