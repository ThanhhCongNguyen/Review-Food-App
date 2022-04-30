package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.model.MealsByCategory

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeal(mealsByCategoryList: ArrayList<MealsByCategory>){
        this.mealsList = mealsByCategoryList
        notifyDataSetChanged()
    }



    class PopularMealViewHolder(var binding: PopularItemsBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        var binding = PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PopularMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide
            .with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}