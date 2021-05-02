package com.ahmettekin.weatherappkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmettekin.weatherappkotlin.databinding.RowLayoutBinding
import com.ahmettekin.weatherappkotlin.getSkyImage
import com.ahmettekin.weatherappkotlin.listener.RecyclerViewListener
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.upperCaseWords
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val myList: List<WeatherModel.WeatherItem>, private val listener:RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private val DEGREE_SYMBOL = "\u2103"
    private val HEAD_OF_ICON_PATH = "https://openweathermap.org/img/wn/"
    private val END_OF_ICON_PATH = "@2x.png"

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bindedItem = myList[position]
        holder.binding.apply {
            tvCityName.text = bindedItem.name
            tvSky.text = bindedItem.weather[0].description.upperCaseWords()
            tvWeatherTemp.text = "Sıcaklık: "+ bindedItem.main.temp.toString()+DEGREE_SYMBOL
            cardView.setBackgroundResource(bindedItem.getSkyImage())
            Picasso.get().load(HEAD_OF_ICON_PATH + bindedItem.weather[0].icon + END_OF_ICON_PATH).into(this.imgWeatherSymbol)
            deleteImage.setOnClickListener {
                listener.recyclerViewItemClick(bindedItem,this.deleteImage)
            }
            root.setOnClickListener {
                listener.recyclerViewItemClick(bindedItem,null)
            }
        }
    }

    override fun getItemCount(): Int = myList.size

}