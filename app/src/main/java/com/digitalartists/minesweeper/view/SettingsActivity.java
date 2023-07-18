package com.digitalartists.minesweeper.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import com.digitalartists.minesweeper.model.Settings;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);
        Context context = getApplicationContext();
        if (savedInstanceState != null) {
            Log.d("1", "");
            settings = savedInstanceState.getParcelable(MainActivity.SETTINGS);
        } else {
            Log.d("2","");
            settings = getIntent().getParcelableExtra(MainActivity.SETTINGS);
        }

        Log.d("ROWS", String.valueOf(settings.getRows()));
        Log.d("COLS", String.valueOf(settings.getCols()));

        Switch isDarkModeOn = findViewById(R.id.switch_id);
        if (settings.getIsDarkMode() == 1) {
            isDarkModeOn.setChecked(true);
        }

        EditText editCols = findViewById(R.id.columns_id);
        editCols.setText(""+settings.getCols());

        EditText editRows = findViewById(R.id.rows_id);
        editRows.setText(""+settings.getRows());

        EditText editMines = findViewById(R.id.numberOfMines_id);
        editMines.setText(""+settings.getMinesNum());

        TextView textError = findViewById(R.id.errorText_id);
        textError.setText("");

        findViewById(R.id.saveSettings_id).setOnClickListener(butPlay -> {
            settings.setCols(Integer.parseInt(String.valueOf(editCols.getText())));
            settings.setRows(Integer.parseInt(String.valueOf(editRows.getText())));
            settings.setMinesNum(Integer.parseInt(String.valueOf(editMines.getText())));

            int res = isEnteredValuesCorrect(settings.getRows(), settings.getCols(), settings.getMinesNum());

            // check if entered values are correct
            if (res == 1) {
                textError.setText("Incorrect number of rows!");
            } else if (res == 2) {
                textError.setText("Incorrect number of columns!");
            } else if (res == 3) {
                textError.setText("Incorrect number of mines!");
            } else {
                textError.setText("");
                if (isDarkModeOn.isChecked()) {
                    settings.setIsDarkMode(1);
                } else {
                    settings.setIsDarkMode(0);
                }

                try {
                    FileProcessing.saveSettings(context, settings);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }

        });

    }

    private int isEnteredValuesCorrect(int rows, int cols, int mines) {
        if (rows <= 0 || rows > 25) {
            return 1;
        }
        if (cols <= 0 || cols > 25) {
            return 2;
        }
        if (mines <= 0 || mines > rows*cols / 3) {
            return 3;
        }
        return 0;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.SETTINGS, settings);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
