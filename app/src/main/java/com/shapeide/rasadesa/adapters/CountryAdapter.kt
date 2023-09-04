package com.shapeide.rasadesa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.domain.Area

class CountryAdapter(val listener: (name: String) -> Unit) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private val arealist: ArrayList<com.shapeide.rasadesa.domain.Area> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_discover_area, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            tv_area.text = arealist[position].strArea
            tv_area.setOnClickListener { listener.invoke(arealist.get(position).strArea) }
        }
    }

    override fun getItemCount(): Int = arealist.size

    fun updateAreaList(newItems : ArrayList<com.shapeide.rasadesa.domain.Area>){
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(Comparator(arealist, newItems))

        this.arealist.clear()
        this.arealist.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_area : TextView = itemView.findViewById(R.id.tv_area)
    }

    inner class Comparator(
        val oldValue: ArrayList<com.shapeide.rasadesa.domain.Area>,
        val newValue: ArrayList<com.shapeide.rasadesa.domain.Area>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).strArea == newValue.get(newItemPosition).strArea

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).strArea.equals(newValue.get(newItemPosition).strArea)
    }

}