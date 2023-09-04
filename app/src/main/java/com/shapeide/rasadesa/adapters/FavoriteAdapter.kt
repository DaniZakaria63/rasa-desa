package com.shapeide.rasadesa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.core.domain.Meal
import java.util.ArrayList

class FavoriteAdapter (val context: Context,val delete: (id: String) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val favoriteData: ArrayList<com.shapeide.rasadesa.core.domain.Meal> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_home_meals, parent, false)
    )

    override fun getItemCount(): Int = favoriteData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data : com.shapeide.rasadesa.core.domain.Meal = favoriteData.get(position)
        holder.tvPreview.text = data.strMeal
        Glide.with(context).load(data.strMealThumb)
            .placeholder(R.drawable.dummy)
            .into(holder.ivPreview)
            .onLoadFailed(context.getDrawable(R.drawable.dummy))
    }

    fun updateData(meals: ArrayList<com.shapeide.rasadesa.core.domain.Meal>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(Comparator(favoriteData, meals))

        this.favoriteData.clear()
        this.favoriteData.addAll(meals)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivPreview = view.findViewById<ImageView>(R.id.iv_preview)
        val tvPreview = view.findViewById<TextView>(R.id.tv_preview)
    }

    inner class Comparator(
        val oldValue: ArrayList<com.shapeide.rasadesa.core.domain.Meal>,
        val newValue: ArrayList<com.shapeide.rasadesa.core.domain.Meal>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).idMeal == newValue.get(newItemPosition).idMeal

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).idMeal.equals(newValue.get(newItemPosition).idMeal)
    }
}