package com.example.kajza.king2.views;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kajza.king2.R;
import com.example.kajza.king2.models.Currency;
import com.example.kajza.king2.network.CurrencyExchangeService;
import com.example.kajza.king2.network.ServiceGenerator;
import com.example.kajza.king2.utils.OpenCSVWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeActivity extends AppCompatActivity {

    private ListView lvCurrency;
    private List<Currency> currencyList;
    private static final String API_URL = "http://api.hnb.hr/";
    private Calendar mCalendar;
    private String mDateString;
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = findViewById(R.id.lvCurrency);
        mCalendar = Calendar.getInstance();
        updateDateString();
        loadCurrencyExchangeData(mDateString);
    }

    public void loadCurrencyExchangeData(String date) {
        CurrencyExchangeService client = ServiceGenerator.createService(CurrencyExchangeService.class, API_URL);
        Call<List<Currency>> currency = client.loadCurrencyExchangeData(date);
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

    private void updateDateString() {
        Date time = mCalendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mDateString = df.format(time);
    }

    public void handleCalendar(View view) {
        if (mDatePickerDialog == null) {
            mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mCalendar.set(year, month, dayOfMonth);
                    updateDateString();
                    loadCurrencyExchangeData(mDateString);
                }
            }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        } else {
            mDatePickerDialog.updateDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        }
        mDatePickerDialog.show();
    }

    public void writeCSV(View view) {
        // todo check if file already exists for selected date
        if (!currencyList.isEmpty()) {
            String[] headers = new String[]{"broj_tecajnice", "datum", "drzava", "sifra_valute", "valuta", "jedinica", "kupovni_tecaj", "srednji_tecaj", "prodajni_tecaj;"};
            List<String[]> values = new ArrayList<>();
            for (Currency currency : currencyList) {
                String[] value = new String[]{currency.getBroj_tecajnice(), currency.getDatum(), currency.getDrzava(), currency.getSifra_valute(), currency.getValuta(), String.valueOf(currency.getJedinica()), currency.getKupovni_tecaj(), currency.getSrednji_tecaj(), currency.getProdajni_tecaj()};
                values.add(value);
            }
            try {
                String path = OpenCSVWriter.writeCSV(headers, values, mDateString, ',');
                Toast.makeText(getBaseContext(), "Written to " + path, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Cannot write to file " + e.getMessage() + ". Check app permissions.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "No currencies to save", Toast.LENGTH_LONG).show();
        }
    }
}
