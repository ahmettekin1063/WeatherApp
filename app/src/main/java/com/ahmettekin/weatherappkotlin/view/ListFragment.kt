package com.ahmettekin.weatherappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.service.WeatherApiClient
import com.ahmettekin.weatherappkotlin.service.WeatherApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

    }

    private fun loadData(){
        val weatherApi=WeatherApiClient.client!!.create(WeatherApiInterface::class.java)
        val compositeDisposable=CompositeDisposable();
        compositeDisposable.add(
            weatherApi.getWeatherData("5174095,1850147,2013159")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableObserver<WeatherModel>(){
                    override fun onNext(weatherModel: WeatherModel) {
                        handleResponse(weatherModel)
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onComplete(){}
                })
        )
    }

    private fun handleResponse(weatherModel: WeatherModel){
        TODO("RecyclerView öğeleri burada yapılacak")
    }
}