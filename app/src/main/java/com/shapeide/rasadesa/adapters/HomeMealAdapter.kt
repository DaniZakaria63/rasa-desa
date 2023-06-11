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
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.utills.CategoryDiffCallback
import com.shapeide.rasadesa.utills.FilterMealDiffCallback

class HomeMealAdapter(val context: Context, private val itemList: ArrayList<FilterMeal>) : RecyclerView.Adapter<HomeMealAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            tv_preview.text = itemList[position].name
            Glide.with(context).load(itemList[position].thumb).placeholder(R.drawable.ic_outline_reload).into(iv_preview)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_home_meals, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateCategoryList(newItems: ArrayList<FilterMeal>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(FilterMealDiffCallback(itemList, newItems))

        this.itemList.clear()
        this.itemList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_preview : TextView = itemView.findViewById(R.id.tv_preview)
        val iv_preview : ImageView = itemView.findViewById(R.id.iv_preview)
    }

}