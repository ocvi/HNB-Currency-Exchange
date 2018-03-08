package com.example.kajza.king2.CurrencyView;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kajza.king2.Currency.CurrencyExchange;
import com.example.kajza.king2.R;
import com.example.kajza.king2.Retrofit.CurrencyExchangeService;
import com.example.kajza.king2.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateCurrency extends AppCompatActivity {

    private static final String API_URL = "http://api.hnb.hr/";
    private ListView lvCurrency;
    private List<CurrencyExchange> currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lvCurrency);
        Intent i = getIntent();
        String intentDate = i.getStringExtra("date");
        loadCurrencyExchangeData(intentDate);
    }

    public void loadCurrencyExchangeData(String date) {
        CurrencyExchangeService client = ServiceGenerator.createService(CurrencyExchangeService.class, API_URL);
        Call<List<CurrencyExchange>> currency = client.loadCurrencyExchangeData(date);
        currency.enqueue(new Callback<List<CurrencyExchange>>() {
            @Override
            public void onResponse(Call<List<CurrencyExchange>> call, Response<List<CurrencyExchange>> response) {
                if (response.isSuccessful()) {
                    currencyList = response.body();
                    ArrayAdapter adapter = new CurrencyAdapter(getApplicationContext(), R.layout.currency_item, (ArrayList<CurrencyExchange>) currencyList);
                    lvCurrency.setAdapter(adapter);
                    lvCurrency.setTextFilterEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<CurrencyExchange>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
