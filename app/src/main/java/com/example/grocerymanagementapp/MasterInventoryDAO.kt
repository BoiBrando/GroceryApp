package com.example.grocerymanagementapp

import androidx.room3.Dao
import androidx.room3.Query

@Dao
interface MasterInventoryDAO {
    @Query("SELECT * FROM MasterGroceryItem")
    fun getMasterGroceryItems(): List<MasterGroceryItem>

    @Query("SELECT * FROM MasterGroceryItem WHERE itemID = :id")
    fun getMasterGroceryItem(id: Int): MasterGroceryItem

    @Query("SELECT * FROM MasterGroceryItem WHERE itemName = :name")
    fun getMasterGroceryItemFromName(name: String): MasterGroceryItem


}