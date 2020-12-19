package com.ahmettekin.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.retrofitjava.R;
import com.ahmettekin.retrofitjava.model.WeatherModel.WeatherItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<WeatherItem> weatherItemList;



    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c","#f5f5f5","#f47932"};

    public RecyclerViewAdapter(ArrayList<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
    }


    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {
        holder.bind(weatherItemList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return weatherItemList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView tvWeatherDescription;
        TextView tvWeatherTemp;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(WeatherItem weatherItem, String[] colors, Integer position) {
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
            tvWeatherDescription = itemView.findViewById(R.id.tvWeatherDescription);
            tvWeatherTemp = itemView.findViewById(R.id.tvWeatherTemp);
            tvWeatherDescription.setText(weatherItem.getWeather()[0].getMain());
            tvWeatherTemp.setText(String.valueOf(weatherItem.getMain().getTemp()));

        }
    }
}
