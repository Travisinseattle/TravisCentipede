package group7.tcss450.uw.edu.centipedeandroid.authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import group7.tcss450.uw.edu.centipedeandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnLogin} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    /**
     * The {@link LoginFragment.OnLogin} instance
     */
    private OnLogin mListener;

    /**
     * Constructs a {@link RegisterFragment}.
     */
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the biew and sets button listeners
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance.
     * @return Inflated view.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button b = (Button) v.findViewById(R.id.submitButton);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.registerButton);
        b.setOnClickListener(this);
        return v;
    }

    /**
     * Attachs the listener to the context.
     * @param context the context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogin) {
            mListener = (OnLogin) context;
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
     * Receives click events for buttons and performs actions based on View id.
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerButton:
                mListener.onRegisterInteraction();
                break;
            default:
                if (mListener !=null) {
                    View test = getView();
                    assert test != null;
                    EditText userText =( EditText)test.findViewById(R.id.userText);
                    String user = userText.getText().toString();
                    EditText passText = ((EditText)test.findViewById(R.id.passText));
                    String pass = passText.getText().toString();
                    //int numDigits = Utilities.getNumberDigits(pass);
                    if (user.equals("") || user.length() < 4) {
                        userText.setError("You must provide a user name 4 digits or longer.!");
                    } else if (pass.equals("") || pass.length() < 6) {
                        passText.setError("You must provide a password 6 digits or longer.!");
                    } else if ( user.equals(pass) || pass.equals(user)) {
                        userText.setError("User and Passwords cannot be the same.");
                        passText.setError("User and Passwords cannot be the same.");
                    } else if (!(pass.toLowerCase().equals(pass)) || !(pass.matches(".*\\d+.*"))) {
                        passText.setError("Password must contain Upper/Lower Case and a Number.");
                    } else {
                            mListener.onLoginInteraction(user, pass);
                    }
                }
                break;
        }
    }

    /**
     * The events for button interactions.
     */
    public interface OnLogin {
        void onLoginInteraction(String user, String pass);
        void onRegisterInteraction();
    }
}
