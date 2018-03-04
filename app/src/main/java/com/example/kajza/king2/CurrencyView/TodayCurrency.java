package com.example.kajza.king2.CurrencyView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TodayCurrency extends AppCompatActivity {

    private ListView lvCurrency;
    private List<CurrencyExchange> currencyList;
    private static final String API_URL = "http://api.hnb.hr/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lvCurrency);
        loadTodayExchangeData();
    }

    private void loadTodayExchangeData() {
        CurrencyExchangeService client = ServiceGenerator.createService(CurrencyExchangeService.class, API_URL);
        Call<List<CurrencyExchange>> currency = client.loadTodayExchangeData();
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
