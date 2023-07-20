package com.digitalartists.minesweeper.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import java.io.IOException;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_results);

        TextView tvResults = findViewById(R.id.results_id);
        Context context = getApplicationContext();
        String results = "";

        try {
            results = FileProcessing.loadResults(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tvResults.setText(results);

        findViewById(R.id.clear_id).setOnClickListener(butPlay -> {
            try {
                FileProcessing.clearResults(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        });

    }

}
