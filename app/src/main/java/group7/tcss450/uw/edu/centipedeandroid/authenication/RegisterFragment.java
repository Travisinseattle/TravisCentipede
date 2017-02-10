package group7.tcss450.uw.edu.centipedeandroid.authenication;

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

    private OnRegister mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Button b = (Button) v.findViewById(R.id.submitButton);
        b.setOnClickListener(this);
        return v;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRegister {
        void onRegisterInteraction(String user, String pass);
    }
}
