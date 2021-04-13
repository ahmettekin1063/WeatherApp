package com.ahmettekin.weatherappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.adapter.RecyclerViewAdapter
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.service.WeatherApiClient
import com.ahmettekin.weatherappkotlin.service.WeatherApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    lateinit var compositeDisposable: CompositeDisposable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    /*private fun loadData() {
        WeatherApiClient.client?.let {
            val weatherApi = it.create(WeatherApiInterface::class.java)
            val compositeDisposable = CompositeDisposable()
            weatherApi.getWeatherData("5174095,1850147,2013159")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.let { observable ->
                    compositeDisposable.add(
                        observable
                            .subscribeWith(object : DisposableObserver<WeatherModel>() {
                                override fun onNext(weatherModel: WeatherModel) {
                                    handleResponse(weatherModel)
                                }

                                override fun onError(e: Throwable) {
                                    println("hata:${e.localizedMessage}")
                                }

                                override fun onComplete() {}
                            })
                    )
                }
        }
    }*/

    private fun loadData(){
        val weatherAPI = WeatherApiClient.client?.create(WeatherApiInterface::class.java)
        compositeDisposable=CompositeDisposable()
        weatherAPI?.getWeatherData("5174095,1850147,2013159")
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableObserver<WeatherModel>() {
                override fun onNext(t: WeatherModel) {
                    handleResponse(t)
                }

                override fun onError(e: Throwable) {
                    println("HATA ÇIKTI -> ${e}")
                }

                override fun onComplete() {}
            })?.let {
                compositeDisposable.add(it)
            }
    }

    private fun handleResponse(weatherModel: WeatherModel) {
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter= weatherModel.list?.let { RecyclerViewAdapter(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}