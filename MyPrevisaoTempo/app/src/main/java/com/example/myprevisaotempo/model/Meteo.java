package com.example.myprevisaotempo.model;


import java.util.ArrayList;

public class Meteo {
    String local;
    String latitude;
    String longitude;
    Hourly hourly;
    Daily daily;

    public Meteo(String local, String latitude, String longitude, Hourly hourly, Daily daily){
        this.local = local;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hourly = hourly;
        this.daily = daily;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}
