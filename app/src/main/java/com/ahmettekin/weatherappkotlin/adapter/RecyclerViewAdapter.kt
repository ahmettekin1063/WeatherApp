package com.ahmettekin.weatherappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmettekin.weatherappkotlin.R
import com.ahmettekin.weatherappkotlin.model.WeatherModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val myList: List<WeatherModel.WeatherItem>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> (){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tvCityName.text=myList[position].name
        holder.itemView.tvSky.text= myList[position].weather?.get(0)?.description
        holder.itemView.tvWeatherTemp.text= myList[position].main?.temp.toString()
    }

    override fun getItemCount(): Int {
        return myList.size
    }
}