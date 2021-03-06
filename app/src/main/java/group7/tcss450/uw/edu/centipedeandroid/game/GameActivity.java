package group7.tcss450.uw.edu.centipedeandroid.game;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.FrameLayout;

import java.math.BigInteger;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.authentication.RegisterFragment;
import group7.tcss450.uw.edu.centipedeandroid.menu.GameOverFragment;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuActivity;
import group7.tcss450.uw.edu.centipedeandroid.menu.MenuFragment;

public class GameActivity extends AppCompatActivity implements GameOverFragment.ReturnToMenuListner, MenuFragment.OnStartGame, MenuFragment.SendSong {

    /*****************************************Fields***********************************************/

    /**
     * Variable that will play music
     */
    private PlayMusicTask mPlayMusicTask;

    /*****************************************Constructor******************************************/


    /**
     * OnCreate method for the activity.  Calculates and instantiates the block size to be used for
     * drawing game objects.
     *
     * @param savedInstanceState The instance state passed to onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Capture the size of the screen as a point and then pass that to the calculateBlocks()
         * method so a suitable block size can be created for rendering purposes.
         */
        mPlayMusicTask = new PlayMusicTask();
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
//            mTask = null;
            if (findViewById(R.id.activity_game) != null) {
                getSupportFragmentManager().beginTransaction().add(R.id.activity_game, new GameFragment()).commit();
            }
        }

        int theSong = getIntent().getIntExtra("int_value", 0);
        if (theSong == 0) {
            mPlayMusicTask.setTrack(293);
            mPlayMusicTask.stopPlayer();
            mPlayMusicTask.execute(293);
        } else if (theSong == 1) {
            mPlayMusicTask.setTrack(294);
            mPlayMusicTask.stopPlayer();
            mPlayMusicTask.execute(292);
        } else if (theSong == 2){
            mPlayMusicTask.setTrack(292);
            mPlayMusicTask.stopPlayer();
            mPlayMusicTask.execute(292);
        }
    }

    /**
     * Method that launches the game over fragment when the game ends.
     */
    public void onGameOver(int score) {
        Bundle b = new Bundle();
        b.putInt("score", score);
        GameOverFragment gameFrag = new GameOverFragment();
        gameFrag.setArguments(b);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_game, gameFrag ,"Game Over")
//                .addToBackStack(null)
                .commit();
    }

    /**
     * Method to stop the Music Player.
     */
    public void stopPlayer() {
        mPlayMusicTask.stopPlayer();
    }

    /**
     * OnPause method to determine behavior when the app pauses
     */
    @Override
    protected void onPause() {
        super.onPause();
//        super.onPause();
       // mGameView.pause();
        mPlayMusicTask.stopPlayer();
    }

    /**
     * Method that overrides the onStop() method to stop the music player.
     */
    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        mPlayMusicTask.stopPlayer();
    }

    /**
     * Method that returns user to the main menu.
     */
    @Override
    public void ReturnToMenu() {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }

    /**
     * required to for implementation
     */
    @Override
    public void onStartGame() {}

    /**
     * required to for implementation
     */
    @Override
    public void onPlayer() {}

    /**
     * required to for implementation
     */
    @Override
    public void songNum(int theSong) {}
}
