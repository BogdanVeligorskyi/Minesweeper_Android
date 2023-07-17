package com.digitalartists.minesweeper.view;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.digitalartists.minesweeper.R;
import com.digitalartists.minesweeper.model.FileProcessing;
import com.digitalartists.minesweeper.model.Settings;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    private Settings settings;
    private int numOfMines;
    private int minesCounter;

    private int[] mines_arr = null;
    private int[] visited_arr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        minesCounter = numOfMines;

        mines_arr = generateMines(settings.getCols() * settings.getRows());
        visited_arr = new int[settings.getCols() * settings.getRows()];

        TableLayout tableLayout;
        ImageView imageButton;

        // create ImageView components (buttons) for game board
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
                imageButton.setId(i * settings.getCols() + j);
                visited_arr[i * settings.getCols() + j] = 0;
                imageButton.setImageResource(R.drawable.non_clicked_cell);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

                // handler for CLICK on this image (button)
                imageButton.setOnClickListener(v -> {
                    ImageView iView = (ImageView) v;
                    Toast.makeText(getApplicationContext(),
                            "You`ve clicked button " + iView.getId(),
                            Toast.LENGTH_SHORT).show();

                    if (visited_arr[iView.getId()] == 2 || visited_arr[iView.getId()] == 1) {
                        return;
                    }

                    // check if clicked button contains mine and
                    // stop the game if it`s true
                    if (checkIfMine(iView.getId())) {
                        setIconToButton(iView, -3);

                        AlertDialog.Builder dialog =
                                new AlertDialog.Builder(this);
                        dialog.setCancelable(false);
                        dialog.setIcon(R.mipmap.ic_launcher_round);
                        dialog.setTitle("Game over");
                        dialog.setMessage("Unfortunately, you`ve lost the game!");
                        dialog.setPositiveButton("Ok", ((dialogInterface, m) -> finish()));
                        dialog.create();
                        dialog.show();

                    }

                    if (checkIfEnd()) {
                        //timer->stop();
                        AlertDialog.Builder dialog =
                                new AlertDialog.Builder(this);
                        dialog.setCancelable(false);
                        dialog.setIcon(R.mipmap.ic_launcher_round);
                        dialog.setTitle("Game over");
                        dialog.setMessage("Congratulations, you`ve won the game!");
                        dialog.setPositiveButton("Ok", ((dialogInterface, m) -> finish()));
                        dialog.create();
                        dialog.show();

                        //addResultToFile(name);
                    }
                });

                // handler for LONG CLICK on this image (button)
                imageButton.setOnLongClickListener(v -> {
                    ImageView iView = (ImageView) v;
                    //Toast.makeText(getApplicationContext(),
                    //        "You`ve LONG clicked button " + iView.getId(),
                    //        Toast.LENGTH_SHORT).show();
                    //iView.setImageResource(R.drawable.flag);
                    iView.setClickable(false);

                    // if cell hasn`t been clicked or visited
                    if (visited_arr[iView.getId()] != 2 && visited_arr[iView.getId()] != 1 && minesCounter > 0) {
                        setIconToButton(iView, -1);
                        minesCounter--;
                        visited_arr[iView.getId()] = 2;

                        // if cell has been already clicked with RMB - return one mine
                    } else if (visited_arr[iView.getId()] == 2) {
                        setIconToButton(iView, -2);
                        minesCounter++;
                        visited_arr[iView.getId()] = 0;
                    }

                    // change label text according to number of mines left
                    String text = "Mines Left: " + minesCounter;
                    tvMinesLeft.setText(text);

                    if (checkIfEnd()) {
                        //timer->stop();

                        AlertDialog.Builder dialog =
                                new AlertDialog.Builder(this);
                        dialog.setCancelable(false);
                        dialog.setIcon(R.mipmap.ic_launcher_round);
                        dialog.setTitle("Game over");
                        dialog.setMessage("Congratulations, you`ve won the game!");
                        dialog.setPositiveButton("Ok", ((dialogInterface, m) -> finish()));
                        dialog.create();
                        dialog.show();
                        //addResultToFile(name);

                        //secondsAfterStart = 0;

                    }

                    return true;
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
            //Log.d("HERE", "12121");
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

    // check if clicked button contains a mine
    boolean checkIfMine(int buttonNumInt) {
        for (int i = 0; i < numOfMines; i++) {
            if (buttonNumInt == mines_arr[i]) {
                return true;
            }
        }
        return false;
    }

    // set icon to button in order to show additional information needed for user
    void setIconToButton(ImageView imageView, int minesNum) {

        if (imageView == null) {
            Log.d("setIconToButton", "Button was not found!");
            return;
        }

        if (minesNum == 0) {
            imageView.setImageResource(R.drawable.empty_cell);
        } else if (minesNum == 1) {
            imageView.setImageResource(R.drawable.digit_1);
        } else if (minesNum == 2) {
            imageView.setImageResource(R.drawable.digit_2);
        } else if (minesNum == 3) {
            imageView.setImageResource(R.drawable.digit_3);
        } else if (minesNum == 4) {
            imageView.setImageResource(R.drawable.digit_4);
        } else if (minesNum == 5) {
            imageView.setImageResource(R.drawable.digit_5);
        } else if (minesNum == 6) {
            imageView.setImageResource(R.drawable.digit_6);
        } else if (minesNum == 7) {
            imageView.setImageResource(R.drawable.digit_7);
        } else if (minesNum == 8) {
            imageView.setImageResource(R.drawable.digit_8);
        } else if (minesNum == -1) {
            imageView.setImageResource(R.drawable.flag);
        } else if (minesNum == -2) {
            imageView.setImageResource(R.drawable.non_clicked_cell);
        } else if(minesNum == -3) {
            imageView.setImageResource(R.drawable.mine_clicked);
        }

    }

    // check if all cells were visited
    boolean checkIfEnd() {
        for (int i = 0; i < settings.getRows()*settings.getCols(); i++) {
            if (visited_arr[i] == 0) {
                return false;
            }
        }
        return true;
    }

}
