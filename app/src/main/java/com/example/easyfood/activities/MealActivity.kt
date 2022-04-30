package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.fragment.HomeFragment
import com.example.easyfood.model.Meal
import com.example.easyfood.videoModel.MealViewModel
import com.example.easyfood.videoModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mealDatabase = MealDatabase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInView()

        mealMvvm.getMealDetails(mealId)
        observerMealDetailLiveData()
        onYoutubeImageClick()
        onFavoriteIconClick()

    }

    private fun onFavoriteIconClick() {
        binding.favoriteIcon.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun onYoutubeImageClick() {
        binding.youtubeImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? = null

    private fun observerMealDetailLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                val meal = t
                mealToSave = meal

                binding.categoryText.text = "Category: ${meal!!.strCategory}"
                binding.locationText.text = "Location: ${meal!!.strArea}"
                binding.mealDetailText.text = meal.strInstructions
                youtubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationInView() {
        Glide
            .with(applicationContext)
            .load(mealThumb)
            .into(binding.imageViewDetail)

        binding.collapsingToolbar.title = mealName
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
        Log.d("TAG", mealId + "/" + mealName + "/"+ mealThumb);
    }
}