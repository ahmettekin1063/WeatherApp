package com.ahmettekin.WeatherApp.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmettekin.WeatherApp.R;


public class CityDetailsFragment extends Fragment {


    TextView textView;
    TextView textView2;


    public CityDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);

        super.onViewCreated(view, savedInstanceState);

        String name = CityDetailsFragmentArgs.fromBundle(requireArguments()).getName();
        float temp = CityDetailsFragmentArgs.fromBundle(requireArguments()).getTemp();

        textView.setText(name);
        textView2.setText("Hissedilen sıcaklık:" + temp);


    }
}