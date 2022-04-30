package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.activities.CategoryMealsActivity
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.adapter.CategoryAdapter
import com.example.easyfood.databinding.FragmentCategoriesBinding
import com.example.easyfood.model.Category
import com.example.easyfood.videoModel.HomeViewModel

class CategoriesFragment : Fragment() {
    private lateinit var bindding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindding = FragmentCategoriesBinding.inflate(inflater)
        return bindding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()
        categoryMealClicked()
    }

    private fun categoryMealClicked() {
        categoryAdapter.onItemClick = {
            category ->
                val intent = Intent(context, CategoryMealsActivity::class.java)
                intent.putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
                startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner, Observer {
            categories ->
                categoryAdapter.setMeal(categoryMeal = categories as ArrayList<Category>)
        })

    }

    private fun prepareRecyclerView() {
        categoryAdapter = CategoryAdapter()
        bindding.recyclerviewCategoryFragment.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }

    }

}