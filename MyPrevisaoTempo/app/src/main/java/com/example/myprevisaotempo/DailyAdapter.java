package com.example.myprevisaotempo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprevisaotempo.model.Daily;
import com.example.myprevisaotempo.model.Meteo;
import com.example.myprevisaotempo.model.Hourly;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class DailyAdapter  extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private ArrayList<Meteo> meteos;

    public DailyAdapter(ArrayList<Meteo> meteo){
        this.meteos = meteo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView weatherText, actuTemperatureText, minTempText, maxTempText, sunriseText, sunsetText;
        private RecyclerView weekTempRecycler, dayTempRecycler;
        private RecyclerView.Adapter myWeekAdapter, myDayAdapter;
        private RecyclerView.LayoutManager layoutManagerOne, layoutManagerTwo;

        public ViewHolder(View view){
            super(view);

            weatherText = view.findViewById(R.id.weathercode_text_view);
            actuTemperatureText = view.findViewById(R.id.temperature_text_view);
            minTempText = view.findViewById(R.id.min_temp_text_view);
            maxTempText = view.findViewById(R.id.max_temp_text_view);
            sunriseText = view.findViewById(R.id.sunrise_time_text_view);
            sunsetText = view.findViewById(R.id.sunset_time_text_view);

            weekTempRecycler = view.findViewById(R.id.temperature_week_rv);
            dayTempRecycler = view.findViewById(R.id.temperature_day_rv);
            layoutManagerOne = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
            layoutManagerTwo = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
            weekTempRecycler.setLayoutManager(layoutManagerOne);
            dayTempRecycler.setLayoutManager(layoutManagerTwo);
        }
    }

    @NonNull
    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_by_location, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Meteo meteo = meteos.get(position);
        Daily daily = meteo.getDaily();
        Hourly hourly = meteo.getHourly();
        GetWeatherPropertyValues proper = new GetWeatherPropertyValues();

        String weat = "OK!";

        //2023-06-13T00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:'00'");
        String currentDateTime = sdf.format(new Date());
        List<String> timeLH = hourly.getTimeList();

        int index = timeLH.indexOf('"' + currentDateTime + '"');

        System.out.print("List Time: ");
        System.out.println(timeLH);
        System.out.println("Time 0: " + timeLH.get(0));
        System.out.println('"' + currentDateTime + '"');
        System.out.print("Index: ");
        System.out.println(currentDateTime.equals(timeLH.get(0)));
        System.out.println("Element in index: " + hourly.getTime(index));

        try {
            System.out.println("CODE_" + hourly.getWeathercode(index));
            weat = proper.getPropValues("CODE_" + Integer.toString(index));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.weatherText.setText(weat);
        holder.actuTemperatureText.setText(hourly.getTemperature_2m(index));
        holder.minTempText.setText(daily.getTemperature_2m_min(0));
        holder.maxTempText.setText(daily.getTemperature_2m_max(0));
        holder.sunriseText.setText(daily.getSunrise(0));
        holder.sunsetText.setText(daily.getSunset(0));

        holder.myDayAdapter = new DayTempAdapter(hourly.getTemperature_2mList().subList(index,index+24), hourly.getTimeList().subList(index,index+24), hourly.getPrecipitation_probabilityList().subList(index,index+24));
        holder.dayTempRecycler.setAdapter(holder.myDayAdapter);

        holder.myWeekAdapter = new WeekTempAdapter(daily.getTimeList().subList(1, 3), daily.getTemperature_2m_maxList().subList(1, 3), daily.getTemperature_2m_minList().subList(1, 3));
        holder.weekTempRecycler.setAdapter(holder.myWeekAdapter);
    }

    @Override
    public int getItemCount() {
        return meteos.size();
    }
}
