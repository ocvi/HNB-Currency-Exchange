package com.example.kajza.king2.Retrofit;


import com.example.kajza.king2.Currency.CurrencyExchange;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyExchangeService {

    @GET("tecajn")
    Call<List<CurrencyExchange>> loadTodayExchangeData();

    @GET("tecajn")
    Call <List<CurrencyExchange>> loadCurrencyExchangeData(@Query("datum") String date);

}