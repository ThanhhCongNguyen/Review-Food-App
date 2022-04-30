package com.example.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.easyfood.model.Meal


@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal(): LiveData<List<Meal>>
}