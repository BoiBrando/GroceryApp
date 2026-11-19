package com.example.grocerymanagementapp

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Delete
import androidx.room3.Update

@Dao
interface GroceryItemDAO {
    //Be able to distinguish things through userid values
    @Query("SELECT * FROM GroceryItem")
    fun getGroceryItems(): List<GroceryItem>

    @Query("SELECT * FROM GroceryItem WHERE userId = :uid")
    fun getGroceryItems(uid: String): List<GroceryItem>

    @Query("SELECT * FROM GroceryItem WHERE ItemName = :name AND userId = :uid")
    fun getGroceryItemFromName(name: String, uid: String): GroceryItem

    @Insert
    suspend fun insert(vararg item: GroceryItem)

    @Query("UPDATE GroceryItem SET Quantity = Quantity + :addQuant WHERE ItemName = :name AND userId = :uid")
    fun updateItemQuantity(name: String, uid: String, addQuant: Double)

    @Query("SELECT EXISTS(SELECT 1 FROM GroceryItem WHERE ItemName = :name AND userId = :uid)")
    fun checkIfItemExists(name: String, uid: String): Boolean

    @Query("DELETE FROM GroceryItem WHERE userId = :uid")
    suspend fun deleteUserGroceries(uid: String)

    @Query("DELETE FROM GroceryItem WHERE itemId = :itemId AND userId = :userId")
    suspend fun deleteItemByUserIdAndItemId(userId: String, itemId: Int)
}