package group7.tcss450.uw.edu.centipedeandroid.game;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by addis on 2/28/2017.
 */

public class PlayMusicTask extends AsyncTask<Integer,Void, String>{
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
                URL url = new URL("https://api.soundcloud.com/tracks/292/stream?client_id=f86c23ad615019f9a1d0bc51cff62a3f");
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
