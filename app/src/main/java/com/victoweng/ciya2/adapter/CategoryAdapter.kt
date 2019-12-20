package com.victoweng.ciya2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.CategoryType
import kotlinx.android.synthetic.main.category_layout.view.*

class CategoryAdapter(val clickListener: (CategoryType) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categoryTypeList: List<CategoryType> = ArrayList()

    fun updateList(list: List<CategoryType>) {
        categoryTypeList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CategoryViewHolder -> {
                holder.bind(categoryTypeList[position], clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryTypeList.size
    }

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val categoryName = itemView.category_name
        val categoryImage = itemView.category_image

        fun bind(categoryType: CategoryType, clickListener: (CategoryType) -> Unit) {
            categoryName.text = categoryType.name
            categoryImage.setBackgroundResource(categoryType.colorRes)
            itemView.setOnClickListener { clickListener(categoryType) }
        }
    }
}