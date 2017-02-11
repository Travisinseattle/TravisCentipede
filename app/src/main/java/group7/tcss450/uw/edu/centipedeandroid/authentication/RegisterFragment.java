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
public class RegisterFragment extends Fragment implements View.OnClickListener {

    /**
     * The {@link OnRegister} instance
     */
    private OnRegister mListener;

    /**
     * Constructs a {@link RegisterFragment}.
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the biew and sets button listeners
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance.
     * @return Inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Button b = (Button) v.findViewById(R.id.submitButton);
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
        if (context instanceof OnRegister) {
            mListener = (OnRegister) context;
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
        if (mListener !=null) {
            View test = getView();
            EditText userText =( EditText)test.findViewById(R.id.userText);
            String user = userText.getText().toString();
            EditText passText = ((EditText)test.findViewById(R.id.passText));
            String pass = passText.getText().toString();
            EditText passConfirmText = ((EditText)test.findViewById(R.id.confirmPassword));
            String passConfirm = passConfirmText.getText().toString();
            if (!user.equals("") && !pass.equals("") &&  pass.equals(passConfirm))
                mListener.onRegisterInteraction(user, pass);
            else {
                if (user.equals("")) {
                    passText.setError("Enter a username!");
                }
                if (pass.equals("")) {
                    passText.setError("Enter a password!");
                }
                if (!pass.equals(passConfirm))
                {
                    passConfirmText.setError("Passwords do not match!");
                }
            }
        }
    }

    /**
     * The events for button interactions.
     */
    public interface OnRegister {
        void onRegisterInteraction(String user, String pass);
    }
}
