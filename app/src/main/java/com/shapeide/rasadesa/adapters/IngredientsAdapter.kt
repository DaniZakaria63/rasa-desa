package com.shapeide.rasadesa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.networks.models.IngredientsModel

class IngredientsAdapter(val items: ArrayList<IngredientsModel>, val listener: OnClickListener) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class OnClickListener(val listener: (name: String) -> Unit) {
        fun onClick(name: String) = listener(name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_discover_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            tv_ingredients.text = items[position].strIngredient
            tv_ingredients.setOnClickListener { listener.onClick(items.get(position).strIngredient) }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val tv_ingredients : TextView = itemView.findViewById(R.id.tv_ingredients)
//        val iv_ingredients : ImageView = itemView.findViewById(R.id.iv_ingredients)
    }
}