package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemPopularBinding
import com.example.foodapp.pojo.CategoryMeals

class PopularFoodAdapter():RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder>() {
    private var mealsList=ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals)->Unit)
    class PopularFoodViewHolder(var binding:ItemPopularBinding):RecyclerView.ViewHolder(binding.root)

    fun setMeals(maalsList:ArrayList<CategoryMeals>){
        this.mealsList=maalsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFoodViewHolder {
        return PopularFoodViewHolder(ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularFoodViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.itemPopularImg)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

    }
}