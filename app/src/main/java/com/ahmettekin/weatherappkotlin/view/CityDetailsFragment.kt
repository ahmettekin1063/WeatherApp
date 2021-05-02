package com.ahmettekin.weatherappkotlin.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.databinding.CityDetailsFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.MessageFormat

class CityDetailsFragment : Fragment(),OnMapReadyCallback {
    private lateinit var imageRef: StorageReference
    private val VALUE_OF_ONE_MEGABYTE: Long = 1024 * 1024
    private var map: GoogleMap?=null
    private var lon= 0f
    private var lat= 0f
    private var cityName=""
    private val WEATHERAPP_IMAGE_PATH = "gs://weatherappkotlin-e993e.appspot.com/images/"
    private val IMAGE_FILE_EXTENSION = ".jpg"
    private val DEGREE_SYMBOL = "\u2103"
    private val VALUE_OF_ZOOM = 10f
    private lateinit var mMapView:MapView
    private var binding: CityDetailsFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CityDetailsFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView=view.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)
        configureUI()
    }

    private fun configureUI() {
        cityName = CityDetailsFragmentArgs.fromBundle(requireArguments()).name
        val feelsLike = CityDetailsFragmentArgs.fromBundle(requireArguments()).feelsLike
        val temp = CityDetailsFragmentArgs.fromBundle(requireArguments()).temp
        val nem = CityDetailsFragmentArgs.fromBundle(requireArguments()).nem
        binding?.textView?.text = cityName
        binding?.textView2?.text = MessageFormat.format("Sıcaklık: {0}{1}\nHissedilen sıcaklık: {2}{3}\nNem Değeri: %{4}", temp, DEGREE_SYMBOL, feelsLike, DEGREE_SYMBOL, nem)
        setImageFromFirestore(cityName)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        lon = CityDetailsFragmentArgs.fromBundle(requireArguments()).lon
        lat = CityDetailsFragmentArgs.fromBundle(requireArguments()).lat
        map = googleMap
        map?.addMarker(MarkerOptions().title(cityName).position(LatLng(lat.toDouble(), lon.toDouble())))
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), lon.toDouble()), VALUE_OF_ZOOM))
    }

    private fun setImageFromFirestore(cityName: String) {
        imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(WEATHERAPP_IMAGE_PATH + cityName + IMAGE_FILE_EXTENSION)
        imageRef.getBytes(4 * VALUE_OF_ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding?.imageView2?.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Log.e("Firebase Error:", "$it.localizedMessage")
        }
    }

    override fun onResume() {
        mMapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mMapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}