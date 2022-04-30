package com.example.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemsBinding
import com.example.easyfood.model.Category
import kotlin.collections.ArrayList

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    class CategoryViewHolder(var binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    private var categoryMeal = ArrayList<Category>()
    lateinit var onItemClick: ((Category) -> Unit)

    fun setMeal(categoryMeal: ArrayList<Category>){
        this.categoryMeal = categoryMeal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var binding = CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide
            .with(holder.itemView)
            .load(categoryMeal[position].strCategoryThumb)
            .into(holder.binding.categoryImg)
        holder.binding.categoryTextview.text = categoryMeal[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryMeal[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryMeal.size
    }
}