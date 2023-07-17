package com.digitalartists.minesweeper.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import com.digitalartists.minesweeper.model.Settings;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String SETTINGS = "SETTINGS";
    private Settings settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            settings = savedInstanceState.getParcelable(SETTINGS);
        } else {
            try {
                settings = FileProcessing.loadSettings(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        Settings finalSettings = settings;
        findViewById(R.id.playButton_id).setOnClickListener(butPlay -> {
                Intent intent = new Intent(this, PlayActivity.class);

                intent.putExtra(SETTINGS, finalSettings);
                startActivity(intent);
        });

        findViewById(R.id.settingsButton_id).setOnClickListener( butPlay -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(SETTINGS, settings);
            Log.d("Settings (ROWS) before", ""+settings.getRows());
            startActivity(intent);
        });
        checkTheme();
    }

    // switch theme after closing 'Options' screen if it was changed
    private void checkTheme() {
        if (settings.getIsDarkMode() == 1) {
            AppCompatDelegate.setDefaultNightMode
                    (AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode
                    (AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.SETTINGS, settings);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("RESUME", "");
        try {
            settings = FileProcessing.loadSettings(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkTheme();
    }

}