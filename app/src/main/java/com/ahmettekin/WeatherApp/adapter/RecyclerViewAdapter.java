package com.ahmettekin.WeatherApp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.model.WeatherModel.WeatherItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.http.GET;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private  final ArrayList<WeatherItem> weatherItemList;



    private final String[] colors = {"#afeeee","#1e90ff","#7fffd4","01d"};

    private final int[] drawables = {R.drawable.mist,R.drawable.snow,R.drawable.rain,R.drawable.clouds,
            R.drawable.clear,R.drawable.thunderstorm,R.drawable.drizzle,R.drawable.unknown};


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
            tvSky= itemView.findViewById(R.id.tvSky);
            itemView.setBackgroundResource(drawables[getSky(weatherItem.getWeather()[0].getMain())]);

            imageView=itemView.findViewById(R.id.imageView);

            tvWeatherDescription.setText(weatherItem.getName());
            tvWeatherTemp.setText("Sıcaklık : "+ weatherItem.getMain().getTemp()+ " \u2103");
            tvSky.setText("Gökyüzü : "+ UpperCaseWords(weatherItem.getWeather()[0].getDescription()));

            String iconId= "http://openweathermap.org/img/wn/"+weatherItem.getWeather()[0].getIcon()+"@2x.png";

            Picasso.get().load(iconId).into(imageView);

        }

        String[] sisliHavalar = {"Mist","Smoke","Haze","Ash","Fog","Dust","Sand","Squall","Tornado"};

        /*
        Aşağıdaki metotta gelen kelimeye göre döndürülen değer, resimlerin bulunduğu
        dizinin indeksini verir.Bu sayede bu indekse göre uygun olan resim seçilmiş olur.
        Resimler de drawables int dizisinin içindedir.
         */

        public int getSky(String main){

            for (String hava : sisliHavalar){
                if(main.matches(hava)) return 0;
            }
            if(main.matches("Snow")) return 1;
            else if(main.matches("Rain")) return 2;
            else if(main.matches("Clouds")) return 3;
            else if(main.matches("Clear"))  return 4;
            else if(main.matches("Thunderstorm")) return 5;
            else if(main.matches("Drizzle")) return 6;


            else return 7;

        }
        //Gelen cümledeki kelimelerin sadece ilk harfleri büyük olacak formata dönüştüren metot

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
