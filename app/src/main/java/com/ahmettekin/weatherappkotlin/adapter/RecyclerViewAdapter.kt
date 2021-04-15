package com.ahmettekin.weatherappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.getSkyImage
import com.ahmettekin.weatherappkotlin.listener.RecyclerViewListener
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import com.ahmettekin.weatherappkotlin.upperCaseWords
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val myList: List<WeatherModel.WeatherItem>, val listener:RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private val DEGREE_SYMBOL = "\u2103"
    private val HEAD_OF_ICON_PATH = "https://openweathermap.org/img/wn/"
    private val END_OF_ICON_PATH = "@2x.png"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tvCityName.text = myList[position].name
        holder.itemView.tvSky.text = myList[position].weather?.get(0)?.description?.upperCaseWords()
        ("Sıcaklık: "+ myList[position].main?.temp.toString()+DEGREE_SYMBOL).also { holder.itemView.tvWeatherTemp.text = it }
        holder.itemView.cardView.setBackgroundResource(myList[position].getSkyImage())
        Picasso.get().load(HEAD_OF_ICON_PATH + myList[position].weather?.get(0)?.icon + END_OF_ICON_PATH).into(holder.itemView.imgWeatherSymbol)
        holder.itemView.deleteImage.setOnClickListener {
            listener.recyclerViewItemClick(myList[position],holder.itemView.deleteImage)
        }
    }

    override fun getItemCount(): Int = myList.size

}