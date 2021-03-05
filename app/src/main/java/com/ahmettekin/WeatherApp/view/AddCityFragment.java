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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ahmettekin.WeatherApp.R;
import com.ahmettekin.WeatherApp.database.LocalDataClass;
import com.ahmettekin.WeatherApp.model.CityModel;
import com.ahmettekin.WeatherApp.service.CityAPI;
import com.ahmettekin.WeatherApp.service.CityService;
import com.ahmettekin.WeatherApp.utils.StringOperations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                getCities(editText.getText().toString());
            } catch (Exception e) {
                System.out.println("Data write error: " + e.getLocalizedMessage());
            }
        });

        readDatabaseButton.setOnClickListener(view -> {
            LocalDataClass.getInstance().readDatabase(view.getContext());
        });
    }

    private void getCities(String cityName){
        CityAPI cityAPI = CityService.getInstance().getRetrofit().create(CityAPI.class);
        Call<List<CityModel>> call = cityAPI.getData();
        call.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    List<CityModel> responseList = response.body();
                    ArrayList<CityModel> cityModels = new ArrayList<>(responseList);
                    boolean sehirBulundu = false;
                    String enteredCityName = StringOperations.upperCaseWords(cityName);

                    for (CityModel cityModel : cityModels) {
                        if (cityModel.name.matches(enteredCityName)) {
                            LocalDataClass.getInstance().writeData(cityModel.name, cityModel.id, getContext());
                            sehirBulundu = true;
                            break;
                        }
                    }

                    if (!sehirBulundu) {
                        Toast.makeText(getContext(), CityService.cityNotFoundText, Toast.LENGTH_SHORT).show();
                    }else{
                        NavDirections navDirections = AddCityFragmentDirections.actionAddCityFragmentToListFragment();
                        Navigation.findNavController(getView()).navigate(navDirections);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                t.printStackTrace();
            }
        });
    }
}