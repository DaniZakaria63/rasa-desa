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

internal class HomeCategoryAdapter(val context: Context, private val items: ArrayList<CategoryModel>, val type: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(type == 1){
            val holder1 : ViewHolder1 = holder as ViewHolder1
            with(holder1){
                tv_category.text = items[position].strCategory
                Glide.with(context).load(items[position].strCategoryThumb).placeholder(R.drawable.ic_outline_reload).into(iv_category)
            }
        }else{
            val holder1 : ViewHolder2 = holder as ViewHolder2
            with(holder1){
                tv_category.text = items[position].strCategory
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
        return if(type==1) ViewHolder1(root1) else ViewHolder2(root2)
    }

    override fun getItemCount(): Int = items.size

    internal inner class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_category : TextView = itemView.findViewById(R.id.tv_category)
        val iv_category : ImageView = itemView.findViewById(R.id.iv_category)
    }

    internal inner class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_category : TextView = itemView.findViewById(R.id.tv_category)
        val iv_category : ImageView = itemView.findViewById(R.id.iv_category)
    }
}