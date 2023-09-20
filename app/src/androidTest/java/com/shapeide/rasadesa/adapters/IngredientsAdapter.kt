package com.shapeide.rasadesa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R

class IngredientsAdapter(val listener: (name: String) -> Unit) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    private val items: ArrayList<com.shapeide.rasadesa.core.domain.Ingredient> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_discover_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            tv_ingredients.text = items[position].strIngredient
            tv_ingredients.setOnClickListener { listener.invoke(items.get(position).strIngredient) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateIngredientList(newList: ArrayList<com.shapeide.rasadesa.core.domain.Ingredient>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            Comparator(items, newList)
        )

        this.items.clear()
        this.items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_ingredients: TextView = itemView.findViewById(R.id.tv_ingredients)
//        val iv_ingredients : ImageView = itemView.findViewById(R.id.iv_ingredients)
    }

    inner class Comparator(
        val oldValue: ArrayList<com.shapeide.rasadesa.core.domain.Ingredient>,
        val newValue: ArrayList<com.shapeide.rasadesa.core.domain.Ingredient>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).idIngredient == newValue.get(newItemPosition).idIngredient

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).idIngredient.equals(newValue.get(newItemPosition).idIngredient)
    }

}