package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.activities.CategoryMealsActivity
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adapter.CategoryAdapter
import com.example.easyfood.adapter.MostPopularAdapter
import com.example.easyfood.databinding.BottomSheetDialogBinding
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.model.Category
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.model.Meal
import com.example.easyfood.videoModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object{
        const val MEAL_ID = "idMeal"
        const val MEAL_NAME = "nameMeal"
        const val MEAL_THUMB = "thumbMeal"
        const val CATEGORY_NAME = "categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        mostPopularAdapter = MostPopularAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerview();
        prepareCategoryMeal();

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClicked()
        viewModel.getCategoryMeal()
        observeCategoryMeal()
        onCategoryMealClicked()

    }

    private fun onCategoryMealClicked() {
        categoryAdapter.onItemClick = {
            categoryMeal ->
                val intent = Intent(activity, CategoryMealsActivity::class.java)
                intent.putExtra(CATEGORY_NAME, categoryMeal.strCategory)
                startActivity(intent)

        }
    }

    private fun observeCategoryMeal() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner, object : Observer<List<Category>> {
            override fun onChanged(t: List<Category>?) {
                val categoryMeal = t
                categoryAdapter.setMeal(categoryMeal = categoryMeal as ArrayList<Category>)
            }

        })
    }

    private fun prepareCategoryMeal() {
        binding.recyclerviewCategory.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = categoryAdapter
        }
    }

    private fun onPopularItemClicked() {
        mostPopularAdapter.onItemClick = {
            meals ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meals.idMeal)
            intent.putExtra(MEAL_NAME, meals.strMeal)
            intent.putExtra(MEAL_THUMB, meals.strMealThumb)
            startActivity(intent)




        }

    }

    private fun preparePopularItemsRecyclerview() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularAdapter
        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
       ) {
           mealList ->
                    mostPopularAdapter.setMeal(mealsByCategoryList = mealList as ArrayList<MealsByCategory>)
       }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomLivedata().observe(viewLifecycleOwner
        ) { t ->
            Glide
                .with(this@HomeFragment)
                .load(t!!.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = t
        }
    }
}