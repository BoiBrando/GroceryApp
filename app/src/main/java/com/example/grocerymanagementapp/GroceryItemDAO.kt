package com.example.grocerymanagementapp

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface GroceryItemDAO {
    @Query("SELECT * FROM GroceryItem")
    fun getGroceryItems(): List<GroceryItem>

    @Query("SELECT * FROM GroceryItem WHERE ItemID = :id")
    fun getGroceryItem(id: Int): GroceryItem

    @Query("SELECT * FROM GroceryItem WHERE ItemName = :name")
    fun getGroceryItemFromName(name: String): GroceryItem

    @Insert
    suspend fun insert(vararg item: GroceryItem)

    @Query("UPDATE GroceryItem SET Quantity = Quantity + :addQuant")
    fun updateItemQuantity(addQuant: Double)

    @Query("SELECT ItemID FROM GroceryItem WHERE EXISTS (SELECT ItemID FROM GroceryItem WHERE ItemID = :id)")
    fun checkIfItemExists(id: Int): Boolean


}