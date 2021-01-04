package com.ahmettekin.WeatherApp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ahmettekin.WeatherApp.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//http://api.openweathermap.org/data/2.5/find?lat=39.92077&lon=32.85411&cnt=50&lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric

        /*ankara merkez ve civarı 50 ilçe
   http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&lang=tr&appid=42cdc93c083ccc6797e8c9643f5470d2&units=metric
 */


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.city_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.add_city_item) {

            NavDirections action = ListFragmentDirections.actionListFragmentToAllCityListFragment();
            Navigation.findNavController(this, R.id.fragment).navigate(action);
        }
        return super.onOptionsItemSelected(item);
    }
}
