package com.example.myprevisaotempo.model;

import android.app.Activity;
import android.content.Context;

import com.example.myprevisaotempo.MainActivity;
import com.example.myprevisaotempo.api.GeocoderService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Geocoder {
    GeocoderService geoService;
    Retrofit retrofit;

    public String lastLocation;

    String local;
    public String[] coord = new String[2];

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }


    public CompletableFuture<String> atCallGeo(String latLon) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://browse.search.hereapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        geoService = retrofit.create(GeocoderService.class);

        Call<JsonObject> call = geoService.GetLocationATJson(latLon);

        CompletableFuture<String> future = new CompletableFuture<>();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject meteo = response.body();
                    JsonArray items = meteo.get("items").getAsJsonArray();
                    String city = items.get(0).getAsJsonObject()
                            .get("address").getAsJsonObject()
                            .get("city").getAsString();
                    future.complete(city);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }


    public CompletableFuture<String[]> qCallGeo(String latLon) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://geocode.search.hereapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        geoService = retrofit.create(GeocoderService.class);

        System.out.println("Qcall");
        Call<JsonObject> call = geoService.GetLocationQJson(latLon);

        CompletableFuture<String[]> future = new CompletableFuture<>();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println(response.raw().toString());
                if (response.isSuccessful()) {
                    System.out.println("Call Successful");
                    JsonObject meteo = response.body();
                    JsonArray items = meteo.get("items").getAsJsonArray();
                    String lat = items.get(0).getAsJsonObject()
                            .get("position").getAsJsonObject()
                            .get("lat").getAsString();
                    String lon = items.get(0).getAsJsonObject()
                            .get("position").getAsJsonObject()
                            .get("lng").getAsString();
                    System.out.println("Lat: " + lat + " Lon: " + lon);

                    future.complete(new String[]{lat, lon});
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Call Failed");
                future.completeExceptionally(t);
            }
        });

        return future;
    }
}