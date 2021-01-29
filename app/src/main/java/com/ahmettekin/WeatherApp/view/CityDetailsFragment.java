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


public class CityDetailsFragment extends Fragment implements OnMapReadyCallback {

    private TextView textView, textView2;
    private ImageView imageView2;
    private StorageReference islandRef;
    private final long ONE_MEGABYTE = 1024 * 1024;
    private MapView mapView;
    private GoogleMap map;
    float lon;
    float lat;
    String cityName;

    public CityDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_city_details, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);


        return v;
        //return inflater.inflate(R.layout.fragment_city_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);
        imageView2 = view.findViewById(R.id.imageView2);


        //mapView = view.findViewById(R.id.mapView);
        //mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);

        cityName = CityDetailsFragmentArgs.fromBundle(requireArguments()).getName();
        float feelsLike = CityDetailsFragmentArgs.fromBundle(requireArguments()).getFeelsLike();
        float temp = CityDetailsFragmentArgs.fromBundle(requireArguments()).getTemp();


        int nem = CityDetailsFragmentArgs.fromBundle(requireArguments()).getNem();


        textView.setText(cityName);
        textView2.setText("Sıcaklık: " + temp + "\u2103" + "\nHissedilen sıcaklık: " + feelsLike + "\u2103" + "\nNem Değeri: %" + nem);
        setImageFromFirestore(cityName);

    }

    public void setImageFromFirestore(String cityName) {
        islandRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://weatherapp-12cde.appspot.com/images/" + cityName + ".jpg");
        islandRef.getBytes(4 * ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        lon = CityDetailsFragmentArgs.fromBundle(requireArguments()).getLon();
        lat = CityDetailsFragmentArgs.fromBundle(requireArguments()).getLat();

        System.out.println(lon);
        System.out.println(lat);

        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);

        }else{
            map.setMyLocationEnabled(true);
        }

        map.addMarker(new MarkerOptions().title(cityName).position(new LatLng(lat,lon)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),10));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    map.addMarker(new MarkerOptions().title(cityName).position(new LatLng(lat,lon)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),10));
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
