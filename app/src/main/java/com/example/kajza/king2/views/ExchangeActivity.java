package com.example.kajza.king2.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kajza.king2.models.Currency;
import com.example.kajza.king2.R;
import com.example.kajza.king2.network.CurrencyExchangeService;
import com.example.kajza.king2.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeActivity extends AppCompatActivity {

    private ListView lvCurrency;
    private List<Currency> currencyList;
    private static final String API_URL = "http://api.hnb.hr/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = findViewById(R.id.lvCurrency);
        Intent i = getIntent();
        String intentDate = i.getStringExtra("date");
        loadCurrencyExchangeData(intentDate);
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

    public void writeCSV() {

    }
}
