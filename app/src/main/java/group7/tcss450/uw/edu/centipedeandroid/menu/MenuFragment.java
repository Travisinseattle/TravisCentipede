package group7.tcss450.uw.edu.centipedeandroid.menu;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import group7.tcss450.uw.edu.centipedeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnStartGame} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // UNUSED

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    // UNUSED
//    private String mParam1;
//    private String mParam2;
    // UNUSED

    /**
     *  Listener for the start of the game.
     */
    private OnStartGame mListener;

    /**
     * int of the song track.
     */
    private int mSong;

    /** Context */
    private Context mContext;

    /**
     * Song listener for track id's.
     */
    private SendSong mSendSong;


    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // UNUSED
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
// UNUSED
    /**
     * Creates the fragment
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSong = 0;
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Creates the view and adds the button listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        Button b = (Button)v.findViewById(R.id.playButton);
        b.setOnClickListener(this);
        b = (Button)v.findViewById(R.id.highScoreButton);
        b.setOnClickListener(this);

        Spinner musicSpinner = (Spinner) v.findViewById(R.id.musicSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.music_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        musicSpinner.setAdapter(adapter);
        musicSpinner.setOnItemSelectedListener(this);
        return v;
    }

    /**
     * Method that checks for items selected in the spinner
     *
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
//        if (pos == 0) {
//            mSendSong.songNum(0);
//        } else {
            mSendSong.songNum(pos);
//        }

    }

    /**
     * Method for if nothing is selected in the spinner.
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {
        mSendSong.songNum(0);
    }


    /**
     * Returns the string name of the song wanted to be played.
     *
     * @return a string representation of the sogn name.
     */
    public int getSong() {
        return mSong;
    }

    /**
     * Attaches the listener to a context.
     * @param context the context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            mSendSong = (MenuFragment.SendSong) context;
        } catch (Exception e){
            Log.e("Send Song", e.getMessage());
        }

        if (context instanceof OnStartGame) {
            mListener = (OnStartGame) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        if (mListener != null) {
            switch (v.getId()) {
                case R.id.playButton:
                    mListener.onStartGame();
                    break;
                case R.id.highScoreButton:
                    mListener.onPlayer();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Listener for the track ID.
     */
    public interface SendSong {
        public void songNum(int theSong);
    }
    /**
     * Listeners for game menu items
     */
    public interface OnStartGame {
        void onStartGame();
        void onPlayer();
    }
}
