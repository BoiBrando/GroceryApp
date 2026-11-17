package com.example.grocerymanagementapp

import androidx.room3.Dao
import androidx.room3.Query

@Dao
interface MasterInventoryDAO {
    @Query("SELECT * FROM GroceryItem")
    fun getGroceryItems(): List<GroceryItem>

    @Query("SELECT * FROM GroceryItem WHERE ItemID = :id")
    fun getGroceryItem(id: Int): GroceryItem

    @Query("SELECT * FROM GroceryItem WHERE ItemName = :name")
    fun getGroceryItemFromName(name: String): GroceryItem


}