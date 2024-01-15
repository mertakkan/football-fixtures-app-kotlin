package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getAllUsersStream(): Flow<List<User>>

    fun getUserStream(id: Int): Flow<User?>

    fun getUserStreamByUsername(username: String): Flow<User?>

    suspend fun insertUser(ind: User)

    suspend fun deleteUser(ind: User)

    suspend fun updateUser(ind: User)
}