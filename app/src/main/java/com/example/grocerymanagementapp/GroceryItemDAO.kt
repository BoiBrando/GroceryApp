package com.example.grocerymanagementapp

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface GroceryItemDAO {
    @Query("SELECT * FROM GroceryItem")
    fun getGroceryItems(): List<GroceryItem>

    @Query("SELECT * FROM GroceryItem WHERE ItemID = :id")
    fun getGroceryItem(id: Int): GroceryItem

    @Insert
    suspend fun insert(vararg item: GroceryItem)


}