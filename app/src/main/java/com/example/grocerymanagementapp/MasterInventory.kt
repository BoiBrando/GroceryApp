package com.example.grocerymanagementapp

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    version = 2,
    entities = [MasterGroceryItem :: class],
    exportSchema = false
)

abstract class MasterInventory: RoomDatabase() {
    abstract val dao: MasterInventoryDAO

    companion object {
        private var INSTANCE: MasterInventory? = null

        fun getDatabase(context: Context): MasterInventory {
            return INSTANCE ?: synchronized(this) {
                val masterDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    MasterInventory::class.java,
                    "MasterInventory"
                ).createFromAsset("database/masterDatabase.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = masterDatabase
                return masterDatabase
            }
        }
    }


}