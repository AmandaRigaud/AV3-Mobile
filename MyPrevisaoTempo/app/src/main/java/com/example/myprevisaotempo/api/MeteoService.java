package com.example.myprevisaotempo.api;

import com.example.myprevisaotempo.model.Meteo;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeteoService {

    @GET("forecast?hourly=temperature_2m,precipitation_probability&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&forecast_days=3&timezone=America%2FSao_Paulo")
    Call<Meteo> GetWeatherObj(@Query("latitude") String lat, @Query("longitude") String lon);

    @GET("forecast?hourly=temperature_2m,precipitation_probability&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset&forecast_days=3&timezone=America%2FSao_Paulo")
    Call<JsonObject> GetWeatherJson(@Query("latitude") String lat, @Query("longitude") String lon);
}
