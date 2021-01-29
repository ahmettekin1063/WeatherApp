package com.ahmettekin.WeatherApp.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmettekin.WeatherApp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CityDetailsFragment extends Fragment {

    private TextView textView, textView2;
    private ImageView imageView2;
    private StorageReference islandRef;
    private final long ONE_MEGABYTE = 1024 * 1024;


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
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        imageView2 = view.findViewById(R.id.imageView2);

        String cityName = CityDetailsFragmentArgs.fromBundle(requireArguments()).getName();
        float feelsLike = CityDetailsFragmentArgs.fromBundle(requireArguments()).getFeelsLike();
        float temp = CityDetailsFragmentArgs.fromBundle(requireArguments()).getTemp();
        int nem = CityDetailsFragmentArgs.fromBundle(requireArguments()).getNem();

        textView.setText(cityName);
        textView2.setText("Sıcaklık: " + temp + "\u2103" + "\nHissedilen sıcaklık: " + feelsLike + "\u2103" + "\nNem Değeri: %" + nem);
        setImage(cityName);

    }

    public void setImage(String cityName){
        islandRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://weatherapp-12cde.appspot.com/images/"+cityName+".jpg");
        islandRef.getBytes(4*ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView2.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                System.out.println(exception.getLocalizedMessage());

            }
        });
    }
}