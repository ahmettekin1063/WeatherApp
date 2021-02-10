package com.ahmettekin.WeatherApp.adapter;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.model.WeatherModel.WeatherItem;
import com.ahmettekin.WeatherApp.view.ListFragmentDirections;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private final ArrayList<WeatherItem> weatherItemList;
    private final String DEGREE_SYMBOL = "\u2103";
    private final String HEAD_OF_ICON_PATH = "http://openweathermap.org/img/wn/";
    private final String END_OF_ICON_PATH = "@2x.png";

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
            ListFragmentDirections.ActionListFragmentToCityDetailsFragment action =
                    ListFragmentDirections
                            .actionListFragmentToCityDetailsFragment(weatherItemList.get(position).getName(),
                                    (float) weatherItemList.get(position).getMain().getFeelsLike(),
                                    (int) weatherItemList.get(position).getMain().getHumidity(),
                                    (float) weatherItemList.get(position).getMain().getTemp(),
                                    (float) weatherItemList.get(position).getCoord().getLon(),
                                    (float) weatherItemList.get(position).getCoord().getLat());
            Navigation.findNavController(v).navigate(action);
        });

        holder.deleteImage.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.deleteImage);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.recycler_menu, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        Toast.makeText(v.getContext(), weatherItemList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        LocalDataClass.getInstance().deleteDatabase(v.getContext(), weatherItemList.get(position).getName());
                        NavDirections navDirections = ListFragmentDirections.actionListFragmentSelf();
                        Navigation.findNavController(v).navigate(navDirections);
                        return true;
                    default:
                }
                return false;
            });
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
        ImageView deleteImage;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(WeatherItem weatherItem) {
            initViews();
            configureUI(weatherItem);
        }

        private void initViews() {
            tvWeatherDescription = itemView.findViewById(R.id.tvWeatherDescription);
            tvWeatherTemp = itemView.findViewById(R.id.tvWeatherTemp);
            tvSky = itemView.findViewById(R.id.tvSky);
            imageView = itemView.findViewById(R.id.imageView);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }

        private void configureUI(WeatherItem weatherItem) {
            WeatherItem.Weather weatherObject;

            if (weatherItem.getWeathers().length > 0) {
                weatherObject = weatherItem.getWeathers()[0];
                itemView.setBackgroundResource(weatherObject.getSkyId());
                tvSky.setText(MessageFormat.format("Gökyüzü : {0}", upperCaseWords(weatherObject.getDescription())));
                String iconURL = HEAD_OF_ICON_PATH + weatherObject.getIcon() + END_OF_ICON_PATH;
                Picasso.get().load(iconURL).into(imageView);
            }

            tvWeatherDescription.setText(weatherItem.getName());
            tvWeatherTemp.setText(MessageFormat.format("Sıcaklık : {0}{1}", weatherItem.getMain().getTemp(), DEGREE_SYMBOL));
        }

        private String upperCaseWords(String line) {
            line = line.trim().toLowerCase();
            String[] data = line.split("\\s");
            StringBuilder lineBuilder = new StringBuilder();

            for (String datum : data) {
                if (datum.length() > 1) {
                    lineBuilder.append(datum.substring(0, 1).toUpperCase()).append(datum.substring(1)).append(" ");
                } else {
                    lineBuilder.append(datum.toUpperCase());
                }
            }

            line = lineBuilder.toString();
            return line.trim();
        }
    }
}
