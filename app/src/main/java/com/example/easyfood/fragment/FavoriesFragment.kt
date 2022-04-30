package com.example.easyfood.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfood.R
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.adapter.FavoritesMealAdapter
import com.example.easyfood.databinding.FragmentFavoriesBinding
import com.example.easyfood.videoModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesMealAdapter: FavoritesMealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesMealAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoritesMealAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoriteRecyclerview)
    }

    private fun prepareRecyclerView() {
        favoritesMealAdapter = FavoritesMealAdapter()
        binding.favoriteRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = favoritesMealAdapter
        }

    }

    private fun observeFavorites() {
       viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer { meals ->
           favoritesMealAdapter.differ.submitList(meals)
       })
    }

}