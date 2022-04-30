package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.model.MealsByCategory

class MealsByCategoryAdapter(): RecyclerView.Adapter<MealsByCategoryAdapter.MealsByCategoryViewHolder>() {

    private var mealsByCategoryList = ArrayList<MealsByCategory>()

    fun setMeal(mealsByCategoryList: ArrayList<MealsByCategory> ){
        this.mealsByCategoryList = mealsByCategoryList
        notifyDataSetChanged()
    }



    class MealsByCategoryViewHolder(var binding: MealItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsByCategoryViewHolder {
        var binding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsByCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsByCategoryList[position].strMealThumb).into(holder.binding.categoryImageItem)
        holder.binding.categoryMealItem.text = mealsByCategoryList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsByCategoryList.size
    }
}