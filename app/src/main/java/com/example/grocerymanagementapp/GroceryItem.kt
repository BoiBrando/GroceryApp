package com.example.grocerymanagementapp

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class GroceryItem(
    @PrimaryKey var ItemID: Int,
//    var InventoryID: String,
//    var CategoryID: String,
    var ItemName: String,
    var Quantity: Double,
    var ExpirationDate: String,
)
