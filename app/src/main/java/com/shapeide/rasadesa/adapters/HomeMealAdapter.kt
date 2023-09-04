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
import com.shapeide.rasadesa.core.domain.FilterMeal

class HomeMealAdapter(
    val context: Context,
    private val callback: (id: String) -> Unit) :
    RecyclerView.Adapter<HomeMealAdapter.ViewHolder>() {
    private val itemList: ArrayList<com.shapeide.rasadesa.core.domain.FilterMeal> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            tv_preview.text = itemList[position].name
            Glide.with(context).load(itemList[position].thumb)
                .placeholder(R.drawable.ic_outline_reload).into(iv_preview)
            iv_preview.setOnClickListener { callback(itemList[position].id.toString()) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_meals, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int = itemList.size

    fun updateCategoryList(newItems: ArrayList<com.shapeide.rasadesa.core.domain.FilterMeal>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(Comparator(itemList, newItems))

        this.itemList.clear()
        this.itemList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_preview: TextView = itemView.findViewById(R.id.tv_preview)
        val iv_preview: ImageView = itemView.findViewById(R.id.iv_preview)
    }

    inner class Comparator(
        val oldValue: ArrayList<com.shapeide.rasadesa.core.domain.FilterMeal>,
        val newValue: ArrayList<com.shapeide.rasadesa.core.domain.FilterMeal>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).id == newValue.get(newItemPosition).id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).name.equals(newValue.get(newItemPosition).name)
    }
}