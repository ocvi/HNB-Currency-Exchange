package com.example.kajza.king2.CSV;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.kajza.king2.Currency.CurrencyExchange;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpenCSVWriter {
    public static final String TAG = "OpenCSVWriter";
    private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";

    public static void writeCSV(Context context) throws IOException {

        File file = new File(context.getFilesDir().getPath() + "/myfile.csv");
        Log.i(TAG, "writeCSV: " + file.getPath());

        Writer writer = new FileWriter(file);

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        String[] headerRecord = {"Name", "Email", "Phone", "Country"};
        csvWriter.writeNext(headerRecord);

        csvWriter.writeNext(new String[]{"Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India"});
        csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
        csvWriter.close();
    }
}