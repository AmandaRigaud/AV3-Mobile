package com.example.myprevisaotempo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {
    @SerializedName("time")
    List<String> time;
    @SerializedName("temperature_2m")
    List<String> temperature_2m ;
    @SerializedName("precipitation_probability")
    List<String> precipitation_probability;

    public List<String> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(List<String> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public List<String> getPrecipitation_probability() {
        return precipitation_probability;
    }

    public void setPrecipitation_probability(List<String> precipitation_probability) {
        this.precipitation_probability = precipitation_probability;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
