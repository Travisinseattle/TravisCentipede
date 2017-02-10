package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.authenication.AuthenicationActivity;
import group7.tcss450.uw.edu.centipedeandroid.authenication.RegisterFragment;
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;

public class MenuActivity extends AppCompatActivity implements MenuFragment.OnStartGame, PlayerFragment.OnPlaySound {
//    private MediaPlayer mMediaPlayer;
    private PlayMusicTask mPlayMusicTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (savedInstanceState == null) {
            if (findViewById(R.id.activity_menu) != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.activity_menu, new MenuFragment()).commit();
            }
        }
    }


    @Override
    public void onStartGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlayer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerFragment fragment = new PlayerFragment();
        fragmentTransaction.replace(R.id.activity_menu, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPlayClick() {
        mPlayMusicTask = new PlayMusicTask();
        mPlayMusicTask.execute(293);
//        playTrack(293);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPlayMusicTask.stopPlayer();

    }

    private class PlayMusicTask extends AsyncTask<Integer,Void, String> {

        private MediaPlayer mMediaPlayer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        @Override
        protected String doInBackground(Integer... params) {
            if (params.length != 1) {
                throw new IllegalArgumentException("One Integer arguments required.");
            }
            String stringURL = "";

            try {
                URL url = new URL("https://api.soundcloud.com/tracks/118483464/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f");
                HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
                ucon.setInstanceFollowRedirects(false);
                URL secondURL = new URL(ucon.getHeaderField("Location"));
                stringURL = secondURL.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringURL;
        }

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

