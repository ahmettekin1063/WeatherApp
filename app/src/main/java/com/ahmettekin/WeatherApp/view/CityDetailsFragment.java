package com.ahmettekin.WeatherApp.view;

import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.ahmettekin.WeatherApp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final String DEGREE_SYMBOL = "\u2103";
    private final float VALUE_OF_ZOOM = 10f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_details, container, false);
        initViews(view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        configureUI();

    }

    private void setImageFromFirestore(String cityName) {

        imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(WEATHERAPP_IMAGE_PATH + cityName + IMAGE_FILE_EXTENSION);
        imageRef.getBytes(4 * VALUE_OF_ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView2.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initViews(View view) {

        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        imageView2 = view.findViewById(R.id.imageView2);
        mapView = view.findViewById(R.id.mapView);

    }

    private void configureUI() {

        cityName = CityDetailsFragmentArgs.fromBundle(requireArguments()).getName();
        float feelsLike = CityDetailsFragmentArgs.fromBundle(requireArguments()).getFeelsLike();
        float temp = CityDetailsFragmentArgs.fromBundle(requireArguments()).getTemp();
        int nem = CityDetailsFragmentArgs.fromBundle(requireArguments()).getNem();

        textView.setText(cityName);
        textView2.setText(MessageFormat.format("Sıcaklık: {0}{1}\nHissedilen sıcaklık: {2}{3}\nNem Değeri: %{4}", temp, DEGREE_SYMBOL, feelsLike, DEGREE_SYMBOL, nem));
        setImageFromFirestore(cityName);

    }

    private boolean checkLocationPermission() {

        return ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        lon = CityDetailsFragmentArgs.fromBundle(requireArguments()).getLon();
        lat = CityDetailsFragmentArgs.fromBundle(requireArguments()).getLat();

        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        if (!checkLocationPermission()) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            requestPermission();
        } else {
            map.setMyLocationEnabled(true);
        }

        map.addMarker(new MarkerOptions().title(cityName).position(new LatLng(lat, lon)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), VALUE_OF_ZOOM));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0) {
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (checkLocationPermission()) {
                    map.setMyLocationEnabled(true);
                    map.addMarker(new MarkerOptions().title(cityName).position(new LatLng(lat, lon)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), VALUE_OF_ZOOM));
                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
