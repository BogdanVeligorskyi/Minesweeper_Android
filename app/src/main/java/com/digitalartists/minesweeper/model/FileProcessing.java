package com.digitalartists.minesweeper.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileProcessing {

    public static final String SETTINGS_FILENAME = "settings.txt";

    public static Settings loadSettings(Context context) throws IOException {
        InputStream is = context.openFileInput(SETTINGS_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String s = "";
        Settings settings = null;
        while ((s = reader.readLine()) != null) {
            Log.d("s.length", ""+s.length());
            if (s.length() == 0) {
                return null;
            }
            String[] valuesSettings = s.split(",");
            settings = new Settings(Integer.parseInt(valuesSettings[0]),
                    Integer.parseInt(valuesSettings[1]),
                    Integer.parseInt(valuesSettings[2]),
                    Byte.parseByte(valuesSettings[3]));

        }
        return settings;
    }


    // save options to settings.txt file
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

}
