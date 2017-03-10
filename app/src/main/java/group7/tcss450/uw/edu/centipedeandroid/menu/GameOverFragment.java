package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;

/**
 * Gameover fragment that can direct the user to play the game again or go back to the main menu.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private ReturnToMenuListner menuListner;
    private FrameLayout frameLayout;

    /**
     * Listener to watch for the game being started.
     */
    private MenuFragment.OnStartGame mListener;

    /**
     * Required empty constructor.
     */
    public GameOverFragment() {

    }

    /**
     * Method that creates the fragment behavior when launched. Creates two buttons for play again
     * and main menu.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return a View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_over, container, false);
        Button b = (Button) v.findViewById(R.id.mainMenu);
        b.setOnClickListener(this);
        frameLayout = (FrameLayout) v.findViewById(R.id.activity_game);
        textView = (TextView) v.findViewById(R.id.score);
        return v;
    }

    /**
     * Attaches the listener to a context.
     * @param context the context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuListner = (ReturnToMenuListner) context;
        if (context instanceof MenuFragment.OnStartGame) {
            mListener = (MenuFragment.OnStartGame) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String score = "0";
        try {
            score = "Score: " + String.valueOf(getArguments().getInt("score"));
        } catch (Exception e) {
            Log.e("GAMEOVER BUNDLE", e.getMessage());
        }
        textView.setText(score);
    }

    /**
     * Detaches the listener
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Emits an event based on the view id.
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainMenu:
                menuListner.ReturnToMenu();
                for (int i = 0; i < 20; i++) {
                    Log.e("MAINMENU: ", "*******************************************************************************************************************************************");
                }
//                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                startActivity(intent);
                MenuFragment menuFrag = new MenuFragment();
                this.getFragmentManager().beginTransaction()
                       .replace(R.id.activity_game, menuFrag, "Menu Fragment")
                        .addToBackStack(null)
                       .commit();
                break;
        }
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
    public interface ReturnToMenuListner {
        void ReturnToMenu();
    }
}
