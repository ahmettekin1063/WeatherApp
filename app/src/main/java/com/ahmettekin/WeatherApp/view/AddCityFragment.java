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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.database.LocalDataClass;

public class AddCityFragment extends Fragment {
    private EditText editText;
    private Button addButton;
    private Button deleteDatabaseButton;
    private String deleteSqlCommand = "DELETE FROM cities";

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
    }

    private void configureListener() {
        addButton.setOnClickListener(v -> {
            LocalDataClass localDataClass = LocalDataClass.getInstance();

            try {
                localDataClass.veriYaz(editText.getText().toString(), getActivity().getApplicationContext());
            } catch (Exception e) {
                System.out.println("Data write error: " + e.getLocalizedMessage());
            }

            NavDirections navDirections = AddCityFragmentDirections.actionAddCityFragmentToListFragment();
            Navigation.findNavController(v).navigate(navDirections);
        });

        deleteDatabaseButton.setOnClickListener(v -> {
            LocalDataClass localDataClass = LocalDataClass.getInstance();
            localDataClass.deleteDatabase(getActivity().getApplicationContext());
        });
    }
}