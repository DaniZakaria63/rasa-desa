package com.shapeide.rasadesa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.models.AreaModel

class CountryAdapter(private val arealist: ArrayList<AreaModel>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_discover_area, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            tv_area.text = arealist[position].strArea
            //TODO: Set the area icons
        }
    }

    override fun getItemCount(): Int = arealist.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_area : TextView = itemView.findViewById(R.id.tv_area)
        val iv_area : ImageView = itemView.findViewById(R.id.iv_area)
    }
}