package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategorymealsBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealByCategory

class FavoritesMealsAdapter():RecyclerView.Adapter<FavoritesMealsAdapter.FavoritesViewHolder>() {
    lateinit var onItemClick:((Meal)->Unit)
    class FavoritesViewHolder(var binding:ItemCategorymealsBinding):RecyclerView.ViewHolder(binding.root) {

    }
    private val diffUtil =object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(ItemCategorymealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val meal=differ.currentList[position]
        holder.binding.itemCategorymealTxt.text=meal.strMeal
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.itemCategorymealImg)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}