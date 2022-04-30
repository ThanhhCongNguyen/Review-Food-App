package com.example.easyfood.db

import android.app.Application

class InventoryApplication: Application() {
    val database: MealDatabase by lazy { MealDatabase.getInstance(this) }
}