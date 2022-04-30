package com.example.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapter.MealsByCategoryAdapter
import com.example.easyfood.databinding.ActivityCategoryMealsBinding
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.videoModel.CategoryMealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var categoryName: String
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealVMD: CategoryMealViewModel
    private lateinit var mealsByCategoryAdapter: MealsByCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealVMD = ViewModelProvider(this)[CategoryMealViewModel::class.java]
        getInfoFromIntent()
        categoryMealVMD.getMealsByCategory(categoryName)
        observeLiveData()

        prepareMealsByCategoryToRecylerView()


    }

    private fun prepareMealsByCategoryToRecylerView() {
        binding.recyclerviewMealbycategory.apply {
            mealsByCategoryAdapter = MealsByCategoryAdapter()
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = mealsByCategoryAdapter
        }
    }

    private fun observeLiveData() {
        categoryMealVMD.observeLiveData().observe(this, object : Observer<List<MealsByCategory>> {
            override fun onChanged(t: List<MealsByCategory>?) {
                mealsByCategoryAdapter.setMeal(mealsByCategoryList = t as ArrayList<MealsByCategory>)
                binding.categoryMealTextview.text = "Total Item: ${t.size.toString()}"
            }

        })
    }

    private fun getInfoFromIntent() {
        var intent = intent
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!


    }
}