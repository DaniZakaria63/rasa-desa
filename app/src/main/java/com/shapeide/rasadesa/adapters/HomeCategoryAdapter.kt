package com.shapeide.rasadesa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.models.CategoryModel

internal class HomeCategoryAdapter(val context: Context, private val items: ArrayList<CategoryModel>) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            tv_category.text = items[position].strCategory
            Glide.with(context).load(items[position].strCategoryThumb).placeholder(R.drawable.ic_outline_reload).into(iv_category)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.item_home_category, parent, false)
        return ViewHolder(root)
    }
    override fun getItemCount(): Int = items.size

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_category : TextView = itemView.findViewById(R.id.tv_category)
        val iv_category : ImageView = itemView.findViewById(R.id.iv_category)
    }
}