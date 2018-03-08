package com.example.kajza.king2.network;


import com.example.kajza.king2.models.Currency;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyExchangeService {

    @GET("tecajn")
    Call<List<Currency>> loadTodayExchangeData();

    @GET("tecajn")
    Call<List<Currency>> loadCurrencyExchangeData(@Query("datum") String date);

}