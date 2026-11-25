package com.example.grocerymanagementapp

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(
    version = 2,
    entities = [GroceryItem :: class],
    exportSchema = false
)

abstract class PersonalDatabase: RoomDatabase() {
    abstract val dao: GroceryItemDAO

    companion object {
        private var INSTANCE: PersonalDatabase? = null

        fun getDatabase(context: Context): PersonalDatabase {
            return INSTANCE ?: synchronized(this) {
                val personalDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    PersonalDatabase::class.java,
                    "PersonalDatabase"
                )
                    .build()
                INSTANCE = personalDatabase
                return personalDatabase
            }
        }
    }


}