package com.example.myprevisaotempo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hourly {
    @SerializedName("time")
    List<String> time;
    @SerializedName("temperature_2m")
    List<String> temperature_2m ;
    @SerializedName("precipitation_probability")
    List<String> precipitation_probability;
    @SerializedName("weathercode")
    List<String> weathercode = new ArrayList<String>();

    public Hourly(List<String> time, List<String> temperature_2m, List<String> precipitation_probability, List<String> weathercode){
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.precipitation_probability = precipitation_probability;
        this.weathercode = weathercode;
    }

    public String getTemperature_2m(int posi) {
        return temperature_2m.get(posi);
    }
    public List<String> getTemperature_2mList() {
        return temperature_2m;
    }

    public void setTemperature_2m(List<String> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public List<String> getPrecipitation_probabilityList() {
        return precipitation_probability;
    }

    public void setPrecipitation_probability(List<String> precipitation_probability) {
        this.precipitation_probability = precipitation_probability;
    }

    public String getTime(int index) {
        return time.get(index);
    }

    public List<String> getTimeList() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getWeathercodeList() {
        return weathercode;
    }
    public String getWeathercode(int index) {
        return weathercode.get(index);
    }

    public void setWeathercode(List<String> weathercode) {
        this.weathercode = weathercode;
    }
}
