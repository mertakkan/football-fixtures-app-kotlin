package com.example.finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val profilePicId: String,
    val favTeam: String,
)