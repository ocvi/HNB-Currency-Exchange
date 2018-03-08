package com.example.kajza.king2.utils;


import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class OpenCSVWriter {
    private static final String TAG = "OpenCSVWriter";

    public static String writeCSV(String[] headers, List<String[]> values, String fileName, char delimiter) throws IOException {
        Log.i(TAG, "writeCSV: Starting.");
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/HNB/CSV/";
        String fullFileName = fileName + ".csv";
        File file = new File(folderPath + fullFileName);
        checkFile(file);

        Writer writer = new FileWriter(file);
        Log.i(TAG, "writeCSV: FileWriter created.");

        CSVWriter csvWriter = new CSVWriter(writer,
                delimiter, // CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        Log.i(TAG, "writeCSV: CSVWriter created.");

        csvWriter.writeNext(headers);
        Log.i(TAG, "writeCSV: Headers written.");

        for (String[] row : values) {
            csvWriter.writeNext(row);
        }
        Log.i(TAG, "writeCSV: Content written");

        csvWriter.close();
        writer.close();
        Log.i(TAG, "writeCSV: Closing writers.");

        return fullFileName;
    }

    private static void checkFile(File file) throws IOException {
        try {
            if (!file.exists()) {
                Log.i(TAG, "checkFile: File path " + file.getPath());
                File dirs = new File(file.getParent());
                if (!dirs.exists()) {
                    Log.i(TAG, "writeCSV: Directories created: " + dirs.mkdirs());
                }
                Log.i(TAG, "writeCSV: New file created: " + file.createNewFile());
            }
        } catch (IOException ex) {
            throw new IOException(file.getAbsolutePath());
        }
    }
}