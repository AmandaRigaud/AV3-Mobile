package com.example.myprevisaotempo.api;

import static android.content.Context.LOCATION_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.example.myprevisaotempo.Manifest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocoderService {
    @GET("?apiKey=TRBiPqYu8ZF4iEr5jJ8SXSQIfHJVR9c_-jqylOz0D7w")
    Call<JsonObject> GetLocationATJson(@Query("at") String latLon);

    @GET("?apiKey=TRBiPqYu8ZF4iEr5jJ8SXSQIfHJVR9c_-jqylOz0D7w")
    Call<JsonObject> GetLocationQJson(@Query("q") String local);

}