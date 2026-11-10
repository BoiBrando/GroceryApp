package com.example.grocerymanagementapp

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    version = 1,
    entities = [GroceryItem :: class],
    exportSchema = false
)

abstract class MasterInventory: RoomDatabase() {
    abstract val dao: GroceryItemDAO

    companion object {
        private var INSTANCE: MasterInventory? = null

        fun getDatabase(context: Context): MasterInventory {
            return INSTANCE ?: synchronized(this) {
                val masterDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    MasterInventory::class.java,
                    "MasterInventory"
                ).build()
                INSTANCE = masterDatabase
                return masterDatabase
            }
        }
    }
//    private var totalItems = 0
//
//    val thingy: GroceryItem = GroceryItem(ItemID = 1, ItemName = "Apple", Quantity = 2.0, ExpirationDate = "12/31/2026")
//    fun addToDB(): Int {
//        masterDatabase.dao.insert(thingy)
//        masterDatabase.incrementTotal()
//        return totalItems
//    }
//
//    fun getTotalItems(): Int {
//        return totalItems
//    }
//
//    fun incrementTotal() {
//        totalItems++
//    }



}