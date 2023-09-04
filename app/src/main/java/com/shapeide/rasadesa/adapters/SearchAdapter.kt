package com.shapeide.rasadesa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.core.domain.Search

class SearchAdapter(private val listener: Listener) :
    ListAdapter<com.shapeide.rasadesa.core.domain.Search, SearchAdapter.ViewHolder>(SEARCH_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_meal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: com.shapeide.rasadesa.core.domain.Search = getItem(position)
        holder.txtSearch.text = data.text.toString()
        holder.txtSearch.setOnClickListener { listener.onDetail(data) }

        /* btnDelete just for local data */
        if(data.is_local){
            holder.btnDelete.visibility = View.VISIBLE
            holder.btnDelete.setOnClickListener { listener.onDelete(data) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtSearch: TextView = view.findViewById(R.id.txt_search)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
    }

    companion object {
        interface Listener {
            fun onDelete(search: com.shapeide.rasadesa.core.domain.Search)
            fun onDetail(search: com.shapeide.rasadesa.core.domain.Search)
        }

        val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<com.shapeide.rasadesa.core.domain.Search>() {
            override fun areItemsTheSame(oldItem: com.shapeide.rasadesa.core.domain.Search, newItem: com.shapeide.rasadesa.core.domain.Search): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: com.shapeide.rasadesa.core.domain.Search, newItem: com.shapeide.rasadesa.core.domain.Search): Boolean {
                return oldItem == newItem
            }

        }
    }
}