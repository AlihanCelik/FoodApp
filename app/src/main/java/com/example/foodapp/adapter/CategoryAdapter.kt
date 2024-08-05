package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategoryBinding
import com.example.foodapp.pojo.Category

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var category=ArrayList<Category>()
    class CategoryViewHolder(var binding: ItemCategoryBinding):RecyclerView.ViewHolder(binding.root) {
    }
    fun setCategories(category:ArrayList<Category>){
        this.category=category
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(category[position].strCategoryThumb).into(holder.binding.itemCategoryImg)

    }
}