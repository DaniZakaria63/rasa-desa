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
import com.shapeide.rasadesa.models.CategoryModel

internal class HomeCategoryAdapter(
    val context: Context,
    private val items: ArrayList<CategoryModel>,
    val type: Int,
    val listener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class OnClickListener(val listener: (name: String) -> Unit) {
        fun onClick(name: String) = listener(name)
    }

    fun updateCategoryList(newItems: ArrayList<CategoryModel>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(CategoryDiffCallback(items, newItems))

        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    internal class CategoryDiffCallback(
        val oldValue: ArrayList<CategoryModel>,
        val newValue: ArrayList<CategoryModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldValue.size

        override fun getNewListSize(): Int = newValue.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).idCategory == newValue.get(newItemPosition).idCategory

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldValue.get(oldItemPosition).strCategory.equals(newValue.get(newItemPosition).strCategory)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (type == 1) {
            val holder1: ViewHolder1 = holder as ViewHolder1
            with(holder1) {
                tv_category.text = items[position].strCategory
                Glide.with(context).load(items[position].strCategoryThumb)
                    .placeholder(R.drawable.ic_outline_reload).into(iv_category)
//                listener.onClick(items.get(position))
                iv_category.setOnClickListener { listener.onClick(items[position].strCategory) }
            }
        } else {
            val holder2: ViewHolder2 = holder as ViewHolder2
            with(holder2) {
                tv_category.text = items[position].strCategory
                tv_category.setOnClickListener { listener.onClick(items[position].strCategory) }
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
}