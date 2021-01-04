package com.ahmettekin.WeatherApp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.model.WeatherModel.WeatherItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private final ArrayList<WeatherItem> weatherItemList;


    public RecyclerViewAdapter(ArrayList<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
    }


    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {

        holder.bind(weatherItemList.get(position));

    }

    @Override
    public int getItemCount() {
        return weatherItemList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView tvWeatherDescription;
        TextView tvWeatherTemp;
        TextView tvSky;
        ImageView imageView;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(WeatherItem weatherItem) {

            tvWeatherDescription = itemView.findViewById(R.id.tvWeatherDescription);
            tvWeatherTemp = itemView.findViewById(R.id.tvWeatherTemp);
            tvSky = itemView.findViewById(R.id.tvSky);
            imageView = itemView.findViewById(R.id.imageView);

            //itemView.setBackgroundResource(getSkyId(weatherItem.getWeather()[0].getMain()));
            itemView.setBackgroundResource(weatherItem.getWeather()[0].getSkyId());


            tvWeatherDescription.setText(weatherItem.getName());
            tvWeatherTemp.setText("Sıcaklık : " + weatherItem.getMain().getTemp() + " \u2103");
            tvSky.setText("Gökyüzü : " + UpperCaseWords(weatherItem.getWeather()[0].getDescription()));

            String iconURL = "http://openweathermap.org/img/wn/" + weatherItem.getWeather()[0].getIcon() + "@2x.png";

            Picasso.get().load(iconURL).into(imageView);

        }


        public String UpperCaseWords(String line) {

            line = line.trim().toLowerCase();
            String data[] = line.split("\\s");
            line = "";
            for (int i = 0; i < data.length; i++) {
                if (data[i].length() > 1)
                    line = line + data[i].substring(0, 1).toUpperCase() + data[i].substring(1) + " ";
                else
                    line = line + data[i].toUpperCase();
            }
            return line.trim();

        }
    }
}
