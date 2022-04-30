package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.FragmentFavoriesBinding
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.model.Meal

class FavoritesMealAdapter: RecyclerView.Adapter<FavoritesMealAdapter.FavoritesViewHolder>() {


    inner class FavoritesViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root){


    }

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.categoryImageItem)
        holder.binding.categoryMealItem.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}