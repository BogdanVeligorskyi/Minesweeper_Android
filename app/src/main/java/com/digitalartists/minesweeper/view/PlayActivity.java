package com.digitalartists.minesweeper.view;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import com.digitalartists.minesweeper.model.Settings;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    private Settings settings;
    private int numOfMines;
    private int[] mines_arr = null;

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

        numOfMines = settings.getMinesNum();

        mines_arr = generateMines(settings.getCols() * settings.getRows());

        TableLayout tableLayout;
        ImageView imageButton;

        for (int i = 0; i < settings.getRows(); i++) {
            tableLayout = findViewById(R.id.buttonsPanel_id);
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);
            for (int j = 0; j < settings.getCols(); j++) {
                imageButton = new ImageView(this);
                imageButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                imageButton.setId(i * settings.getRows() + j);
                //imageButton.setImageResource(R.drawable.mine_usual);
                //Log.d("BEFORE_LOOP", "kk");
                /*for (int k = 0; k < mines_arr.length; k++) {
                    //Log.d("MINES_ARR", ""+mines_arr[k]);
                    if (mines_arr[k] == imageButton.getId()) {
                        imageButton.setImageResource(R.drawable.mine_usual);
                        break;
                    }
                    if (k == mines_arr.length - 1) {

                    }
                }*/
                imageButton.setImageResource(R.drawable.non_clicked_cell);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

                // handler for CLICK on this image (button)
                imageButton.setOnClickListener(v -> {
                    ImageView iView = (ImageView) v;
                    Toast.makeText(getApplicationContext(),
                            "You`ve clicked button " + iView.getId(),
                            Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < mines_arr.length; k++) {
                        if (mines_arr[k] == iView.getId()) {
                            Toast.makeText(getApplicationContext(),
                                    "This is a mine!",
                                    Toast.LENGTH_SHORT).show();
                            iView.setImageResource(R.drawable.mine_clicked);
                            break;
                        }
                    }
                });

                // handler for LONG CLICK on this image (button)
                imageButton.setOnLongClickListener(v -> {
                    ImageView iView = (ImageView) v;
                    Toast.makeText(getApplicationContext(),
                            "You`ve LONG clicked button " + iView.getId(),
                            Toast.LENGTH_SHORT).show();
                    iView.setImageResource(R.drawable.flag);
                    iView.setClickable(false);
                    return false;
                });

                tableRow.addView(imageButton);
            }
            tableLayout.addView(tableRow, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            }


        }

        private int[] generateMines(int square) {
            mines_arr = new int[numOfMines];
            int min = 1;
            int max = square - 1;
            Log.d("HERE", "12121");
            for (int i = 0; i < numOfMines; i++) {
                boolean isUnique = false;
                while (!isUnique) {
                    int randNum = (int) (Math.random() * (max - min + 1) + min);
                    for (int k = 0; k <= i; k++) {
                        if (mines_arr[k] == randNum) {
                            isUnique = false;
                            break;
                        }
                        if (k == i) {
                            mines_arr[i] = randNum;
                            isUnique = true;
                        }
                    }
                }
            }

            for (int j = 0; j < this.numOfMines; j++) {
                Log.d("CREATED_ARR", "" + mines_arr[j]);
            }

            return mines_arr;
        }

}
