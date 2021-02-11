package com.ahmettekin.WeatherApp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.service.CityService;

public class AddCityFragment extends Fragment {
    private EditText editText;
    private Button addButton, readDatabaseButton;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

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
        readDatabaseButton = view.findViewById(R.id.readDatabaseButton);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void configureListener() {
        addButton.setOnClickListener(view -> {
            try {
                progressBar.setVisibility(View.VISIBLE);
                CityService.getInstance().writeDataLocalDatabase(editText.getText().toString(), view);
            } catch (Exception e) {
                System.out.println("Data write error: " + e.getLocalizedMessage());
            }
        });

        readDatabaseButton.setOnClickListener(view -> {
            LocalDataClass.getInstance().readDatabase(view.getContext());
        });
    }
}