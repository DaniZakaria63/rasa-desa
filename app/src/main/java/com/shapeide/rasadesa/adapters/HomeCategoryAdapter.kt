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
import com.shapeide.rasadesa.core.domain.Category

internal class HomeCategoryAdapter(
    val context: Context,
    val type: Int,
    val listener: (name: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<com.shapeide.rasadesa.core.domain.Category> = ArrayList()

    fun updateCategoryList(newItems: ArrayList<com.shapeide.rasadesa.core.domain.Category>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(Comparator(items, newItems))

        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (type == 1) {
            val holder1: ViewHolder1 = holder as ViewHolder1
            with(holder1) {
                tv_category.text = items[position].name
                Glide.with(context).load(items[position].thumb)
                    .placeholder(R.drawable.ic_outline_reload).into(iv_category)
//                listener.onClick(items.get(position))
                iv_category.setOnClickListener { listener.invoke(items[position].name) }
            }
        } else {
            val holder2: ViewHolder2 = holder as ViewHolder2
            with(holder2) {
                tv_category.text = items[position].name
                tv_category.setOnClickListener { listener.invoke(items[position].name) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root1 = inflater.inflate(R.layout.item_home_category, parent, false)
        val root2 = inflater.inflate(R.layout.item_discover_category, parent, false)
        return if (type == 1) ViewHolder1(root1) else ViewHolder2(root2)
    }

    override fun getItemCount(): Int = items.size

    internal inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_category: TextView = itemView.findViewById(R.id.tv_category)
        val iv_category: ImageView = itemView.findViewById(R.id.iv_category)
    }

    internal inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_category: TextView = itemView.findViewById(R.id.tv_category)
    }

    inner class Comparator(
        val oldValue: ArrayList<com.shapeide.rasadesa.core.domain.Category>,
        val newValue: ArrayList<com.shapeide.rasadesa.core.domain.Category>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).id == newValue.get(newItemPosition).id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).name.equals(newValue.get(newItemPosition).name)
    }
}