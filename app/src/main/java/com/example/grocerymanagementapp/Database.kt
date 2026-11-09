package com.example.grocerymanagementapp

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    version = 1,
    entities = [
        User :: class
    ],
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract val dao: UserDAO
}