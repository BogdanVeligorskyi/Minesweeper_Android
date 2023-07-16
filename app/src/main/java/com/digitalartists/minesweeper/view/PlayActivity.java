package com.digitalartists.minesweeper.view;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import com.digitalartists.minesweeper.model.Settings;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if (savedInstanceState != null) {
            settings = savedInstanceState.getParcelable(MainActivity.SETTINGS);
        } else {
            try {
                settings = FileProcessing.loadSettings(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TextView tvMinesLeft = findViewById(R.id.minesLeft_id);
        TextView tvTime = findViewById(R.id.time_id);

        tvMinesLeft.setText("Mines Left: " + settings.getMinesNum());

        TableLayout tableLayout;
        ImageView imageButton;

        float weight = (float) (1.0 / settings.getCols());

        for (int i = 0; i < settings.getRows(); i++) {
            tableLayout = (TableLayout) findViewById(R.id.buttonsPanel_id);
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    0, weight));
            for (int j = 0; j < settings.getCols(); j++) {
                imageButton = new ImageView(this);
                imageButton.setLayoutParams(new TableRow.LayoutParams(0,
                        TableRow.LayoutParams.MATCH_PARENT, weight));
                imageButton.setId(i*settings.getRows() + settings.getCols());
                imageButton.setImageResource(R.drawable.digit_1);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                tableRow.addView(imageButton);
            }
            tableLayout.addView(tableRow);
        }


    }

}
