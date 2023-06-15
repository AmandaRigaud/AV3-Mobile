package com.example.myprevisaotempo.model;

import android.content.Context;

import com.example.myprevisaotempo.api.GeocoderService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Geocoder {
    GeocoderService geoService;
    Retrofit retrofit;
    String local;
    String[] coord = new String[2];

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void atCallGeo(String latLon){
        retrofit = new Retrofit.Builder().baseUrl("https://browse.search.hereapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        geoService = retrofit.create(GeocoderService.class);

        Call<JsonObject> call = geoService.GetLocationATJson(latLon);

        call.enqueue((new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonObject meteo = response.body();
                    JsonArray items = meteo.get("items").getAsJsonArray();
                    setLocal(items.get(0).getAsJsonObject().get("address").getAsJsonObject().get("city").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        }));
    }

    public String[] qCallGeo(String local){
        retrofit = new Retrofit.Builder().baseUrl("https://geocode.search.hereapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        geoService = retrofit.create(GeocoderService.class);


        Call<JsonObject> call = geoService.GetLocationQJson(local);

        call.enqueue((new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonObject meteo = response.body();
                    JsonArray items = meteo.get("items").getAsJsonArray();
                    coord[0] = items.get(0).getAsJsonObject().get("position").getAsJsonObject().get("lat").getAsString();
                    coord[1] = items.get(0).getAsJsonObject().get("position").getAsJsonObject().get("lon").getAsString();
                    System.out.println("Lat:" + coord[0] + " Lon:" + coord[1]);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        }));

        return coord;
    }
}
