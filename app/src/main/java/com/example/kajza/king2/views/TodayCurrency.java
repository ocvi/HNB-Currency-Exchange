package com.example.kajza.king2.views;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kajza.king2.R;
import com.example.kajza.king2.models.Currency;
import com.example.kajza.king2.network.CurrencyExchangeService;
import com.example.kajza.king2.network.ServiceGenerator;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayCurrency extends AppCompatActivity {

    private static final String API_URL = "http://api.hnb.hr/";
    Context appContext;
    private ListView lvCurrency;
    private List<Currency> currencyList;
//    private Button csv_button;
    private String filename = "SampleFile";
    //private String filepath = "MyFileStorage";
    //File myExternalFile;
    //private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lvCurrency);
        loadTodayExchangeData();
        appContext = getApplicationContext();

//        csv_button = (Button) findViewById(R.id.csv_button);
//        csv_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    writeCSV();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }

    private void loadTodayExchangeData() {
        CurrencyExchangeService client = ServiceGenerator.createService(CurrencyExchangeService.class, API_URL);
        Call<List<Currency>> currency = client.loadTodayExchangeData();
        currency.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                if (response.isSuccessful()) {
                    currencyList = response.body();
                    ArrayAdapter adapter = new CurrencyAdapter(getApplicationContext(), R.layout.currency_item, (ArrayList<Currency>) currencyList);
                    lvCurrency.setAdapter(adapter);
                    lvCurrency.setTextFilterEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveCSVfile() {
        try (
                //Writer writer = Files.newBufferedWriter(Paths.get(getStorageDir()));
                Writer writer = new FileWriter("/mnt/sdcard/myfile.csv");

                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        )

        {
            String[] headerRecord = {"Broj tečajnice", "Datum", "Država", "Šifra valute", "Valuta", "Jedinica",
                    "Kupovni tečaj", "Srednji tečaj", "Prodajni tečaj"};
            csvWriter.writeNext(headerRecord);

            Iterator<Currency> it = currencyList.iterator();
            while (it.hasNext()) {
                Currency emp = it.next();
                String[] bodyRecord = {emp.getBroj_tecajnice(), emp.getDatum(), emp.getDrzava(),
                        emp.getSifra_valute(), emp.getValuta(), emp.getJedinica().toString(), emp.getKupovni_tecaj(),
                        emp.getSrednji_tecaj(), emp.getProdajni_tecaj()};
                //Log.i("While : " + bodyRecord);
                csvWriter.writeNext(bodyRecord);
            }

            csvWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStorageDir() {
        return this.getExternalFilesDir(null).getAbsolutePath();
        //  return "/storage/emulated/0/Android/data/com.iam360.sensorlog/";
    }

    private void saveCSVfile2() {
        try {
            String[] headerRecord = {"Broj tečajnice", "Datum", "Država", "Šifra valute", "Valuta", "Jedinica",
                    "Kupovni tečaj", "Srednji tečaj", "Prodajni tečaj"};

            File file = new File(filename + ".csv");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(headerRecord));

            Iterator<Currency> it = currencyList.iterator();
            while (it.hasNext()) {
                Currency emp = it.next();
                String[] bodyRecord = {emp.getBroj_tecajnice(), emp.getDatum(), emp.getDrzava(),
                        emp.getSifra_valute(), emp.getValuta(), emp.getJedinica().toString(), emp.getKupovni_tecaj(),
                        emp.getSrednji_tecaj(), emp.getProdajni_tecaj()};
                bw.write(String.valueOf(bodyRecord));
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeCSV() throws IOException {
        File file = new File(getFilesDir().getPath() + "/myfile.csv");
        // Log( "writeCSV: " + file.getPath());

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



