package com.example.myprevisaotempo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Daily {
    @SerializedName("time")
    List<String> time = new ArrayList<String>();
    @SerializedName("temperature_2m_max")
    List<String> temperature_2m_max = new ArrayList<String>();
    @SerializedName("temperature_2m_min")
    List<String> temperature_2m_min = new ArrayList<String>();
    @SerializedName("sunrise")
    List<String> sunrise = new ArrayList<String>();
    @SerializedName("sunset")
    List<String> sunset = new ArrayList<String>();

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(List<String> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public List<String> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(List<String> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public List<String> getSunrise() {
        return sunrise;
    }

    public void setSunrise(List<String> sunrise) {
        this.sunrise = sunrise;
    }

    public List<String> getSunset() {
        return sunset;
    }

    public void setSunset(List<String> sunset) {
        this.sunset = sunset;
    }

}
