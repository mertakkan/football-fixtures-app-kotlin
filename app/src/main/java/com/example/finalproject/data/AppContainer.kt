package com.example.finalproject.data

import android.content.Context

interface AppContainer {
    val userRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userRepository: UsersRepository by lazy {
        OfflineUsersRepository(UserDatabase.getDatabase(context).userDao())
    }
}