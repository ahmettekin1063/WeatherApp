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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.MessageFormat;


public class CityDetailsFragment extends Fragment implements OnMapReadyCallback {
    private TextView textView, textView2;
    private ImageView imageView2;
    private StorageReference imageRef;
    private final long VALUE_OF_ONE_MEGABYTE = 1024 * 1024;
    private MapView mapView;
    private GoogleMap map;
    private float lon, lat;
    private String cityName;
    private final String WEATHERAPP_IMAGE_PATH = "gs://weatherapp-12cde.appspot.com/images/";
    private final String IMAGE_FILE_EXTENSION = ".jpg";
    private final String DEGREE_SYMBOL = "\u2103";
    private final float VALUE_OF_ZOOM = 10f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        configureUI();
    }

    private void setImageFromFirestore(String cityName) {
        imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(WEATHERAPP_IMAGE_PATH + cityName + IMAGE_FILE_EXTENSION);
        imageRef.getBytes(4 * VALUE_OF_ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView2.setImageBitmap(bitmap);
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        imageView2 = view.findViewById(R.id.imageView2);
        mapView = view.findViewById(R.id.mapView);
    }

    private void configureUI() {
        cityName = CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getName();
        float feelsLike = (float) CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getMain().getFeelsLike();
        float temp = (float) CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getMain().getTemp();
        int nem = (int) CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getMain().getHumidity();
        textView.setText(cityName);
        textView2.setText(MessageFormat.format("Sıcaklık: {0}{1}\nHissedilen sıcaklık: {2}{3}\nNem Değeri: %{4}", temp, DEGREE_SYMBOL, feelsLike, DEGREE_SYMBOL, nem));
        setImageFromFirestore(cityName);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        lon = (float) CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getCoord().getLon();
        lat = (float) CityDetailsFragmentArgs.fromBundle(requireArguments()).getWeatherItem().getCoord().getLat();
        map = googleMap;
        map.addMarker(new MarkerOptions().title(cityName).position(new LatLng(lat, lon)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), VALUE_OF_ZOOM));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
