package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import group7.tcss450.uw.edu.centipedeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnPlaySound} interface
 * to handle interaction events.
 */
public class PlayerFragment extends Fragment implements View.OnClickListener {

    /**
     * {@link OnPlaySound} listener instance
     */
    private OnPlaySound mListener;

    /**
     * Empty Constructor
     */
    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the view and sets the button listener.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        Button b = (Button) v.findViewById(R.id.soundPlay);
        b.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    /**
     * Click listener for play button.
     * @param v the view.
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onPlayClick();
        }
    }

    /**
     * Attaches the listener to a context.
     * @param context the context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlaySound) {
            mListener = (OnPlaySound) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Detaches the listener.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Emit emitted on button click.
     */
    public interface OnPlaySound {
        void onPlayClick();
    }
}
