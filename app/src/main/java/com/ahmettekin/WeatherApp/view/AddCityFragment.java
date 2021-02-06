package com.ahmettekin.WeatherApp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.service.CityService;

public class AddCityFragment extends Fragment {
    private EditText editText;
    private Button addButton, deleteDatabaseButton, readDatabaseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        configureListener();
    }

    private void initViews(View view) {
        editText = view.findViewById(R.id.editText);
        addButton = view.findViewById(R.id.addButton);
        deleteDatabaseButton = view.findViewById(R.id.deleteDatabaseButton);
        readDatabaseButton = view.findViewById(R.id.readDatabaseButton);
    }

    private void configureListener() {
        addButton.setOnClickListener(view -> {
            try {
                CityService.getInstance().writeDataLocalDatabase(editText.getText().toString(), view);
            } catch (Exception e) {
                System.out.println("Data write error: " + e.getLocalizedMessage());
            }
        });

        deleteDatabaseButton.setOnClickListener(view -> {
            LocalDataClass.getInstance().deleteDatabase(view.getContext());
        });

        readDatabaseButton.setOnClickListener(view -> {
            LocalDataClass.getInstance().readDatabase(view.getContext());
        });
    }
}