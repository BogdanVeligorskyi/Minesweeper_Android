package com.digitalartists.minesweeper.model;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileProcessing {

    // filenames
    public static final String SETTINGS_FILENAME = "settings.txt";
    public static final String RESULTS_FILENAME = "results.txt";


    // load settings from settings.txt file
    public static Settings loadSettings(Context context) throws IOException {

        Settings settings = null;
        File file = context.getFileStreamPath(SETTINGS_FILENAME);

        // if application is run for the first time - don`t read settings.txt -
        if (!file.exists()) {
            settings = new Settings(10, 10, 15, 0);
            return settings;
        }

        // otherwise read settings from file
        InputStream is = context.openFileInput(SETTINGS_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String s;
        while ((s = reader.readLine()) != null) {
            Log.d("s.length", ""+s.length());
            if (s.length() == 0) {
                break;
            }
            String[] valuesSettings = s.split(",");
            settings = new Settings(Integer.parseInt(valuesSettings[0]),
                    Integer.parseInt(valuesSettings[1]),
                    Integer.parseInt(valuesSettings[2]),
                    Byte.parseByte(valuesSettings[3]));

        }
        return settings;
    }


    // save settings to settings.txt file
    public static void saveSettings(Context context, Settings settings) throws IOException {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter
                            (context.openFileOutput(SETTINGS_FILENAME,
                                    Context.MODE_PRIVATE));
            outputStreamWriter.write("" + settings.getCols() + ","
                    + settings.getRows() + ","
                    + settings.getMinesNum() + ","
                    + settings.getIsDarkMode()
            );
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }


    // append result to results.txt file
    public static void saveResult(Context context, String resultStr) throws IOException {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter
                            (context.openFileOutput(RESULTS_FILENAME,
                                    Context.MODE_APPEND));
            outputStreamWriter.write(resultStr + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // load results from results.txt file
    public static String loadResults(Context context) throws IOException {
        File file = context.getFileStreamPath(RESULTS_FILENAME);

        // if application is run for the first time - don`t read results.txt -
        if (!file.exists()) {
            return "";
        }

        // otherwise read settings from file
        InputStream is = context.openFileInput(RESULTS_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String s;
        String resultStr = "";
        while ((s = reader.readLine()) != null) {
            Log.d("s.length", ""+s.length());
            resultStr += s;
            resultStr += "\n";
            if (s.length() == 0) {
                break;
            }
        }
        Log.d("TEXT", resultStr);
        return resultStr;
    }


    // clear results in results.txt file
    public static void clearResults(Context context) throws IOException {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter
                            (context.openFileOutput(RESULTS_FILENAME,
                                    Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
