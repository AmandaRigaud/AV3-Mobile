package com.example.myprevisaotempo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Daily {

    List<String> time;
    List<String> temperature_2m_max;
    List<String> temperature_2m_min;
    List<String> sunrise;
    List<String> sunset;

    public Daily(List<String> time, List<String> temperature_2m_max, List<String> temperature_2m_min, List<String> sunrise, List<String> sunset) {
        this.time = time;
        this.temperature_2m_max = temperature_2m_max;
        this.temperature_2m_min = temperature_2m_min;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getTime(int posi) {
        return time.get(posi);
    }

    public List<String> getTimeList() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public String getTemperature_2m_max(int posi) {
        return temperature_2m_max.get(posi);
    }

    public List<String> getTemperature_2m_maxList() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(List<String> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public String getTemperature_2m_min(int posi) {
        return temperature_2m_min.get(posi);
    }

    public List<String> getTemperature_2m_minList() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(List<String> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public String getSunrise(int posi) {
        return sunrise.get(posi);
    }

    public List<String> getSunriseList() {
        return sunrise;
    }

    public void setSunrise(List<String> sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset(int posi) {
        return sunset.get(posi);
    }

    public List<String> getSunsetList(int posi) {
        return sunset;
    }

    public void setSunset(List<String> sunset) {
        this.sunset = sunset;
    }
}
