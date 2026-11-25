package com.example.grocerymanagementapp

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class GroceryItem(
    @PrimaryKey(autoGenerate = true) var localID: Int = 0,
//  Items can have the same itemID, but will not cause an error since they will have
//  different localID values
    var itemId: Int,
    var itemName: String,
    var quantity: Double,
    var expirationDate: String,
    var foodType: String,
    val userId: String
)
