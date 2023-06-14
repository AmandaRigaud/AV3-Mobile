package com.example.myprevisaotempo.api;

import com.example.myprevisaotempo.model.Meteo;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeteoService {

    //TODO: Se timezone=auto, ele pega a timezone de acordo com as coordenadas. Minha ideia: Na primeira chamada dessa api, qaundo pegamos o local, podemos pegar a timezone e salvar para usar nas outras chamadas

    @GET("forecast?hourly=temperature_2m,precipitation_probability,weathercode&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,weathercode&forecast_days=3&timezone=America%2FSao_Paulo")
    Call<Meteo> GetWeatherObj(@Query("latitude") String lat, @Query("longitude") String lon);

    @GET("forecast?hourly=temperature_2m,precipitation_probability,weathercode&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,weathercode&forecast_days=3&timezone=America%2FSao_Paulo")
    Call<JsonObject> GetWeatherJson(@Query("latitude") String lat, @Query("longitude") String lon);
}
