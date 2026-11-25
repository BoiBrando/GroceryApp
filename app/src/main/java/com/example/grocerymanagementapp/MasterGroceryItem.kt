package com.example.grocerymanagementapp

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class MasterGroceryItem (
    @PrimaryKey var itemID: Int,
    var itemName: String,
    var quantity: Double,
    var expirationDate: String,
    var foodType: String
)
