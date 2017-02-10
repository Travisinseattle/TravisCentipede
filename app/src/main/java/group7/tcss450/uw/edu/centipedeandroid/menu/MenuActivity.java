package group7.tcss450.uw.edu.centipedeandroid.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;

public class MenuActivity extends AppCompatActivity implements MenuFragment.OnStartGame {
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
}

