package com.example.myprevisaotempo.model;

import com.google.gson.annotations.SerializedName;

public class Meteo {

    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;
    @SerializedName("timezone")
    String timezone;
    @SerializedName("hourly")
    Hourly hourly;
    @SerializedName("daily")
    Daily daily;
}
