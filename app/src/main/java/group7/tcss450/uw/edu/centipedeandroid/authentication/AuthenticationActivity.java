package group7.tcss450.uw.edu.centipedeandroid.authentication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

/**
 * An activity to authenticate an user.
 */
public class AuthenticationActivity extends AppCompatActivity implements LoginFragment.OnLogin, RegisterFragment.OnRegister {
    /**
     * The partial URL to the PHP files.
     */
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~nmousel/";

    /**
     * The task related to the authenication happening.
     */
    private AsyncTask<String,Void,String> mTask;

    /**
     * Creates a login fragment if there is no previous state.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenication);
        if (savedInstanceState == null) {
            mTask = null;
            if (findViewById(R.id.activity_authenication) != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.activity_authenication, new LoginFragment()).commit();
            }
        }
    }

    /**
     * Listener that handles an User login.
     * @param user The Username.
     * @param pass The Password.
     */
    @Override
    public void onLoginInteraction(final String user, final String pass) {
        mTask = new PostLoginTask();
        mTask.execute(PARTIAL_URL, user, pass);
    }

    /**
     * Replaces the fragment with the RegisterFragment.
     */
    @Override
    public void onRegisterInteraction() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final RegisterFragment fragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.activity_authenication, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    /**
     * Listener that handles an User registration.
     * @param user The Username.
     * @param pass The Password.
     */
    @Override
    public void onRegisterInteraction(final String user, final String pass) {
        mTask = new PostRegisterTask();
        mTask.execute(PARTIAL_URL, user, pass);
    }

    /**
     * Called when an user successfully validates.
     */
    private void onSuccess()
    {
        final Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    /**
     * A task to login an user.
     */
    private class PostLoginTask extends AsyncTask<String, Void, String> {
        /**
         * The service of the task.
         */
        private final String SERVICE = "login.php";

        /**
         * Sends the user information to the service for validation and authentication
         * @param strings PARTIAL_URL, Username, Password
         * @return if successful, string JSON object else error string
         */
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            final String url = strings[0];
            try {
                final URL urlObject = new URL(url + SERVICE);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                final OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                final String data = URLEncoder.encode("login", "UTF-8") +
                        "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" +
                        URLEncoder.encode(strings[2], "UTF-8");
                wr.write(data);
                wr.flush();
                final InputStream content = urlConnection.getInputStream();
                final BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s;
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return response;
        }

        /**
         * Parses the user JSON or displays error string.
         * @param result Error string or user JSON.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to") || result.startsWith("Error:")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            } else  {
                String user = null;
                String pass = null;
                try {
                    final JSONObject jo = new JSONObject(result);
                    user = jo.getString("user");
                    pass = jo.getString("pass");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (user != null && pass != null)
                    onSuccess();
            }
        }
    }

    /**
     * A task for authenticating an User registration.
     */
    private class PostRegisterTask extends AsyncTask<String, Void, String> {
        /**
         * The service for the task.
         */
        private final String SERVICE = "register.php";

        /**
         * Sends the user information to the service for validation and authentication
         * @param strings PARTIAL_URL, Username, Password
         * @return if successful, string JSON object else error string
         */
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            final String url = strings[0];
            try {
                final URL urlObject = new URL(url + SERVICE);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                final OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                final String data = URLEncoder.encode("login", "UTF-8") + "=" +
                        URLEncoder.encode(strings[1], "UTF-8") + "&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" +
                        URLEncoder.encode(strings[2], "UTF-8");
                wr.write(data);
                wr.flush();
                final InputStream content = urlConnection.getInputStream();
                final BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s;
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return response;
        }

        /**
         * Parses the user JSON or displays error string.
         * @param result Error string or user JSON.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
//            Log.d("TEST", "doInBackground: " + result);
            if (result.startsWith("Unable to") || result.startsWith("Error:")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            } else {
                String user = null;
                String pass = null;
                try {
                    final JSONObject jo = new JSONObject(result);
                    user = jo.getString("user");
                    pass = jo.getString("pass");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (user != null && pass != null) {
                    onSuccess();
                }
            }

        }
    }

}
