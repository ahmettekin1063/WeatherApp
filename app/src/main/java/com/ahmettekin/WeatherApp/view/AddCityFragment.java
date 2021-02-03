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
import com.ahmettekin.WeatherApp.database.YerelVeriSinifi;

public class AddCityFragment extends Fragment {

    private EditText editText;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        button = view.findViewById(R.id.button);
    }

    private void  configureListener(){


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YerelVeriSinifi yerelVeriSinifi = new YerelVeriSinifi();
                try{
                    yerelVeriSinifi.veriYaz(editText.getText().toString(),getActivity().getApplicationContext());
                }catch (Exception e){
                    System.out.println("veri yazma hatası: "+e.getLocalizedMessage());
                }

                NavDirections navDirections = AddCityFragmentDirections.actionAddCityFragmentToListFragment();
                Navigation.findNavController(v).navigate(navDirections);

            }
        });
    }
}