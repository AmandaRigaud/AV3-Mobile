package com.example.myprevisaotempo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeekTempAdapter extends RecyclerView.Adapter<WeekTempAdapter.ViewHolder> {
    private List<String> date;
    private List<String> temperatures_max;
    private List<String> temperatures_min;

    public WeekTempAdapter(List<String> date, List<String> temperatures_max, List<String>temperatures_min){
        this.date = date;
        this.temperatures_max = temperatures_max;
        this.temperatures_min = temperatures_min;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dateText, maxTempText, minTempText;

        public ViewHolder(View view){
            super(view);

            dateText = view.findViewById(R.id.time_day_text_view);
            maxTempText = view.findViewById(R.id.tempM_td_text_view);
            minTempText = view.findViewById(R.id.tempMi_td_text_view);
        }
    }

    @NonNull
    @Override
    public WeekTempAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.temperature_week, parent, false);
        return  new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        String[] dateSplit = date.get(position).split("T");
        String[] dateD = dateSplit[0].split("-");

        holder.dateText.setText(dateD[2].substring(0, dateD[2].length() - 1)+"-"+dateD[1]);
        holder.maxTempText.setText(temperatures_max.get(position));
        holder.minTempText.setText(temperatures_min.get(position));
    }

    @Override
    public int getItemCount(){
        return date.size();
    }
}
