package com.example.grocerymanagementapp

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class User (
    @PrimaryKey var userID: Int,
    var username: String,
    var password: String,
    var email: String
)
