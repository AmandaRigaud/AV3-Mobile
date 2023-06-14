package com.example.myprevisaotempo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DayTempAdapter extends RecyclerView.Adapter<DayTempAdapter.ViewHolder> {

    private List<String> time;
    private List<String> temperature;
    private List<String> rainPercent;

    public DayTempAdapter(List<String> time, List<String> temperature, List<String> rainPercent){
        this.time = time;
        this.temperature = temperature;
        this.rainPercent = rainPercent;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tempText, hourText, rainPText;

        public ViewHolder(View view){
            super(view);

            tempText = view.findViewById(R.id.temp_td_text_view);
            hourText = view.findViewById(R.id.time_day_text_view);
            rainPText = view.findViewById(R.id.rain_percent_text_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temperature_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        String[] hourS = time.get(position).split("T");

        holder.hourText.setText(hourS[1]+"H");
        holder.tempText.setText(temperature.get(position));
        holder.rainPText.setText(rainPercent.get(position)+"%");
    }

    @Override
    public int getItemCount() {
        return time.size();
    }
}
