package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;

/**
 * An activity to handle the game main menu.
 */
public class MenuActivity extends AppCompatActivity implements MenuFragment.OnStartGame,
        MenuFragment.SendSong {

    /**
     * A task to play music
     */
    private PlayMusicTask mPlayMusicTask;

    private int mSong;

    private MenuFragment.SendSong mSengSong;

    /**
     * Creates the view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (savedInstanceState == null) {
            if (findViewById(R.id.activity_menu) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_menu, new MenuFragment())
                        .commit();
            }
        }
    }

    /**
     * Starts {@link GameActivity}
     */
    @Override
    public void onStartGame() {
        Intent intent = new Intent(this, GameActivity.class).putExtra(
                "int_value", mSong);
        startActivity(intent);
    }

    /**
     * Replaces the current fragment with the SoundTest fragment
     */
    @Override
    public void onPlayer() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_menu, new HighScoreFragment())
                .addToBackStack(null)
                .commit();
    }

    public void setSong(int theSong) {
        mSong = theSong;
    }

    /**
     * Stops the player on back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mPlayMusicTask.stopPlayer();
        } catch (Exception e) {
            Log.e("StopPlayer fail", e.getMessage());
        }
    }


    /**
     * Method that sets the song track id.
     * @param theSong
     */
    @Override
    public void songNum(int theSong) {
        mSong = theSong;
    }

    /**
     * Method that saves the high scores for the current game.
     *
     * @param context
     * @param key
     * @param list
     * @param <T>
     */
    public static <T> void saveHighScores(final Context context,
                                          final String key,
                                          final List<T> list) {
        final Gson gson = new Gson();
        final String json = gson.toJson(list);

        updateScores(context, key, json);
    }

    /**
     * Method that updates the high scores.
     * @param context
     * @param key
     * @param value
     */
    private static void updateScores(final Context context,
                                     final String key,
                                     final String value) {
        final SharedPreferences.Editor setPrefs =
                context.getSharedPreferences(context.getString(R.string.scores_preference),
                        Context.MODE_PRIVATE).edit();
        setPrefs.putString(key, value);
        setPrefs.apply();
    }

    /**
     * Getter method that returns the current highscores
     *
     * @param context
     * @param key
     * @return
     */
    public static List<HighScore> getHighScores(final Context context,
                                                final String key) {
        final SharedPreferences getPrefs =
                context.getSharedPreferences(context.getString(R.string.scores_preference),
                        Context.MODE_PRIVATE);

        final Gson gson = new Gson();
        List<HighScore> highScores;
        final String listOfScores = getPrefs.getString(key, "");

        Type type = new TypeToken<List<HighScore>>() {}.getType();
        highScores = gson.fromJson(listOfScores, type);

        return highScores;
    }


    /**
     * A task for playing music from SoundCloud
     */
    private class PlayMusicTask extends AsyncTask<Integer,Void, String> {
        /**
         * The MedisPlsyer instance
         */
        private MediaPlayer mMediaPlayer;

        /**
         * Sets up the MediaPlayer
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        /**
         * Gets the right url
         * @param params the track to get.
         * @return the redirected URL
         */
        @Override
        protected String doInBackground(Integer... params) {
            if (params.length != 1) {
                throw new IllegalArgumentException("One Integer arguments required.");
            }
            String stringURL = "";

            try {
                // Follows the URL redirects
                URL url = new URL("https://api.soundcloud.com/tracks/293/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f");
                HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
                ucon.setInstanceFollowRedirects(false);
                URL secondURL = new URL(ucon.getHeaderField("Location"));
                stringURL = secondURL.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringURL;
        }

        /**
         * Plays the song from the link.
         * @param theURL source of song
         */
        @Override
        protected void onPostExecute(String theURL) {
            super.onPostExecute(theURL);
            try {
                mMediaPlayer.setDataSource(theURL);
                mMediaPlayer.prepare();
//                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mp.start();
//                    }
//                });
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Stops the player
         */
        public void stopPlayer()
        {
            if(mMediaPlayer != null)
            {
                if (mMediaPlayer.isPlaying())
                {
                    mMediaPlayer.stop();
                }
            }
        }
    }

//    public void playTrack(int trackID){
//        if (mMediaPlayer == null) {
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            try {
//                //https://api.soundcloud.com/tracks/"TRACK ID"/stream?client_id="YOUR CLIENT ID"
//                //293
//                // https://api.soundcloud.com/tracks/293/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f
////                URL url = new URL("https://api.soundcloud.com/tracks/293/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f");
////                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                URL url = new URL("https://api.soundcloud.com/tracks/293/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f");
//                HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
//                ucon.setInstanceFollowRedirects(false);
//                URL secondURL = new URL(ucon.getHeaderField("Location"));
//
//
//
////                HttpResponse resp = wrapper.get(Request.to(res));
////                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
////                    final Header location = resp.getFirstHeader("Location");
////                    if (location != null && location.getValue() != null) {
////                        String redirectedStream = location.getValue();
////                        //...
////                    }
////                }
//
////                mMediaPlayer.setDataSource("https://api.soundcloud.com/tracks/" + trackID + "/stream?client_id=" + "f86c23ad615019f9a1d0bc51cff62a3f");
//                Log.d("TEST", secondURL.toString());
//                mMediaPlayer.setDataSource(secondURL.toString());
//                mMediaPlayer.prepare();
//                mMediaPlayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            if (mMediaPlayer.isPlaying()) {
//                mMediaPlayer.stop();
//                mMediaPlayer.reset();
//            }
//
//        }
//
//    }
}

