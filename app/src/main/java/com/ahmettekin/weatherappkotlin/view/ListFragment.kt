package com.ahmettekin.weatherappkotlin.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.adapter.RecyclerViewAdapter
import com.ahmettekin.weatherappkotlin.database.LocalDatabase
import com.ahmettekin.weatherappkotlin.listener.RecyclerViewListener
import com.ahmettekin.weatherappkotlin.model.City
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.removeFromSuperView
import com.ahmettekin.weatherappkotlin.service.WeatherApiClient
import com.ahmettekin.weatherappkotlin.service.WeatherApiInterface
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mContext: Context
    private var mContainer: ViewGroup? = null
    private lateinit var mSearchableSpinner: SearchableSpinner
    private lateinit var alertView: View
    private var selectedCity: City? = null
    private var requestQueue:RequestQueue?=null
    private lateinit var recyclerViewAdapter:RecyclerViewAdapter
    private val databaseEmptyWarningText = "veritabanı boş"
    private lateinit var mView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContainer = container
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization(view)
        loadData()
        fabConfigure()
        spinnerConfigure()
    }

    private fun initialization(view: View) {
        mView=view
        mContext = view.context
        requestQueue=Volley.newRequestQueue(mContext)
        alertView = layoutInflater.inflate(R.layout.alert_view_layout, mContainer, false)
        mSearchableSpinner = alertView.findViewById(R.id.searchableSpinner)
    }

    private fun loadData() {
        val weatherAPI = WeatherApiClient.client?.create(WeatherApiInterface::class.java)
        compositeDisposable = CompositeDisposable()
        weatherAPI?.getWeatherData(LocalDatabase.getCityIdFromDatabase(mContext))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableObserver<WeatherModel>() {
                override fun onNext(t: WeatherModel) {
                    handleResponse(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("Hata:", "$e")
                }

                override fun onComplete() {}
            })?.let {
                compositeDisposable.add(it)
            }
    }

    private fun handleResponse(weatherModel: WeatherModel) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        val myList= weatherModel.list as ArrayList
        recyclerViewAdapter= RecyclerViewAdapter(myList, object : RecyclerViewListener {

            override fun recyclerViewItemClick(weatherItem: WeatherModel.WeatherItem, deleteView: View?) {
                if (deleteView != null) {
                    val popupMenu = PopupMenu(mContext, deleteView)
                    val inflater = popupMenu.menuInflater
                    inflater.inflate(R.menu.recycler_delete_item, popupMenu.menu)
                    popupMenu.show()
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.action_delete -> {
                                LocalDatabase.deleteCityFromDatabase(mContext, weatherItem.id)
                                if (LocalDatabase.getCityIdFromDatabase(mContext) == "") {
                                    myList.clear()
                                    recyclerViewAdapter.notifyDataSetChanged()
                                    Toast.makeText(context, databaseEmptyWarningText, Toast.LENGTH_SHORT).show()
                                } else {
                                    loadData()
                                }
                            }
                        }
                        true
                    }
                }else{
                    val action=ListFragmentDirections.actionListFragmentToCityDetailsFragment(weatherItem.name
                    ,weatherItem.main.feelsLike.toFloat(),weatherItem.main.humidity,weatherItem.main.temp.toFloat()
                    ,weatherItem.coord.lon.toFloat(),weatherItem.coord.lat.toFloat())
                    Navigation.findNavController(mView).navigate(action)
                }
            }
        })
        recyclerView.adapter=recyclerViewAdapter
    }

    private fun spinnerConfigure() {
        val cityNameList = ArrayList<String>()
        val cityList = ArrayList<City>()
        val url = "https://raw.githubusercontent.com/ahmettekin1063/CityListJson/master/city.list.tr.json"
        val jsonArrayRequest = JsonArrayRequest(url,
            {
                for (i in 0 until it.length()) {
                    val city = it.getJSONObject(i)
                    val city_id = city.getInt("id")
                    val city_name = city.getString("name")
                    cityList.add(City(city_name, city_id))
                    cityNameList.add(city_name)
                }
            }
        ) {
            Log.e("HATA: ", "{${it.localizedMessage}}")
        }
        jsonArrayRequest.tag="JsonArrayRequest"
        requestQueue?.add(jsonArrayRequest)

        val spnAdapter = ArrayAdapter(mContext, R.layout.spinner_one_line, cityNameList)
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSearchableSpinner.setTitle("Şehir Seç")
        mSearchableSpinner.setPositiveButton("SEÇ")
        mSearchableSpinner.adapter = spnAdapter
        mSearchableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCity = cityList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun fabConfigure() {
        fab.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(mContext)
            alertView.removeFromSuperView()
            alertDialogBuilder.setMessage("Şehir Seçin")
            alertDialogBuilder.setView(alertView)
            alertDialogBuilder.setPositiveButton("Tamam")
            { _, _ ->
                selectedCity?.let { selectedCity ->
                    LocalDatabase.writeData(selectedCity.name, selectedCity.id, mContext)
                    loadData()
                }
            }

            alertDialogBuilder.setNegativeButton("İptal ")
            { _, _ -> }
            alertDialogBuilder.create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll("JsonArrayRequest")
    }
}