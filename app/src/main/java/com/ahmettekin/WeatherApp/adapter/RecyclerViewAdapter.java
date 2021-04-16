package com.ahmettekin.WeatherApp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.listener.RecyclerViewOnClickListener;
import com.ahmettekin.WeatherApp.model.WeatherModel.WeatherItem;
import com.ahmettekin.WeatherApp.utils.StringOperations;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private final ArrayList<WeatherItem> weatherItemList;
    private final String DEGREE_SYMBOL = "\u2103";
    private final String HEAD_OF_ICON_PATH = "http://openweathermap.org/img/wn/";
    private final String END_OF_ICON_PATH = "@2x.png";
    private RecyclerViewOnClickListener listener;

    public RecyclerViewAdapter(RecyclerViewOnClickListener listener, ArrayList<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
        this.listener = listener;
    }

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
        holder.itemView.setOnClickListener(v -> {
            listener.recyclerViewItemViewClick(weatherItemList.get(position),null);
        });
        holder.deleteImage.setOnClickListener(v -> {
            listener.recyclerViewItemViewClick(weatherItemList.get(position), holder.deleteImage);
        });
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
        public ImageView deleteImage;
        CardView cardView;
        View itemView;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
        }

        public void bind(WeatherItem weatherItem) {
            initViews();
            configureUI(weatherItem);
        }

        private void initViews() {
            cardView=itemView.findViewById(R.id.cardView);
            tvWeatherDescription = cardView.findViewById(R.id.tvCityName);
            tvWeatherTemp = cardView.findViewById(R.id.tvWeatherTemp);
            tvSky = cardView.findViewById(R.id.tvSky);
            imageView = cardView.findViewById(R.id.imageView);
            deleteImage = cardView.findViewById(R.id.deleteImage);
        }

        private void configureUI(WeatherItem weatherItem) {
            WeatherItem.Weather weatherObject;
            if (weatherItem.getWeathers().length > 0) {
                weatherObject = weatherItem.getWeathers()[0];
                cardView.setBackgroundResource(weatherObject.getSkyId());
                tvSky.setText(MessageFormat.format("Gökyüzü : {0}", StringOperations.upperCaseWords(weatherObject.getDescription())));
                String iconURL = HEAD_OF_ICON_PATH + weatherObject.getIcon() + END_OF_ICON_PATH;
                Picasso.get().load(iconURL).into(imageView);
                tvWeatherDescription.setText(weatherItem.getName());
                tvWeatherTemp.setText(MessageFormat.format("Sıcaklık : {0}{1}", weatherItem.getMain().getTemp(), DEGREE_SYMBOL));
            }
        }
    }
}
