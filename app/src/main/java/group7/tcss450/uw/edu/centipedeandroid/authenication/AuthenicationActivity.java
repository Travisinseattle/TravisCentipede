package group7.tcss450.uw.edu.centipedeandroid.authenication;

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
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;

public class AuthenicationActivity extends AppCompatActivity implements LoginFragment.OnLogin, RegisterFragment.OnRegister {
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~nmousel/";
    AsyncTask<String,Void,String> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenication);
        if (savedInstanceState == null) {
            task = null;
            if (findViewById(R.id.activity_authenication) != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.activity_authenication, new LoginFragment()).commit();
            }
        }
    }
    @Override
    public void onLoginInteraction(String user, String pass) {
        task = new PostLoginTask();
        task.execute(PARTIAL_URL, user, pass);
    }

    @Override
    public void onRegisterInteraction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment fragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.activity_authenication, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onRegisterInteraction(String user, String pass) {
        task = new PostRegisterTask();
        task.execute(PARTIAL_URL, user, pass);
    }

    private void onSucess(String user, String pass)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private class PostLoginTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "login.php";

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            try {
                URL urlObject = new URL(url + SERVICE);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");
                wr.write(data);
                wr.flush();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
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

        @Override
        protected void onPostExecute(String result) {         // Something wrong with the network or the URL.
//            Log.d("TEST", "doInBackground: " + result);
            if (result.startsWith("Unable to") || result.startsWith("Error:")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                return;
            } else  {
                String user = null;
                String pass = null;
                try {
                    JSONObject jo = new JSONObject(result);
                    user = jo.getString("user");
                    pass = jo.getString("pass");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (user != null && pass != null)
                    onSucess(user, pass);
            }
        }
    }

    private class PostRegisterTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "register.php";

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            try {
                URL urlObject = new URL(url + SERVICE);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8") + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");
                wr.write(data);
                wr.flush();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
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

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
//            Log.d("TEST", "doInBackground: " + result);
            if (result.startsWith("Unable to") || result.startsWith("Error:")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                return;
            } else {
                String user = null;
                String pass = null;
                try {
                    JSONObject jo = new JSONObject(result);
                    user = jo.getString("user");
                    pass = jo.getString("pass");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (user != null && pass != null) {
//                    Log.d("TEST", "doInBackground: " + user);
                    onSucess(user, pass);
                }
            }

        }
    }

}
