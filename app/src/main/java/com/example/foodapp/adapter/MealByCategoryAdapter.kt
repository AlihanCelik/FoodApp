package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategorymealsBinding
import com.example.foodapp.pojo.MealByCategory

class MealByCategoryAdapter():RecyclerView.Adapter<MealByCategoryAdapter.MealByCategoryViewHolder>() {
    private var mealByCategory=ArrayList<MealByCategory>()
    lateinit var onItemClick:((MealByCategory)->Unit)
    class MealByCategoryViewHolder(var binding: ItemCategorymealsBinding):RecyclerView.ViewHolder(binding.root)
    fun setMealByCategory(maalsList: List<MealByCategory>){
        this.mealByCategory=maalsList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealByCategoryViewHolder {
        return MealByCategoryViewHolder(ItemCategorymealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealByCategory.size
    }

    override fun onBindViewHolder(holder: MealByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealByCategory[position].strMealThumb).into(holder.binding.itemCategorymealImg)
        holder.binding.itemCategorymealTxt.text=mealByCategory[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealByCategory[position])
        }

    }
}