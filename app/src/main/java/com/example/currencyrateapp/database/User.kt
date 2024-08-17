package com.example.currencyrateapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val age: Int
)
