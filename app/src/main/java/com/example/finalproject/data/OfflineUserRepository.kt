package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) :
    UsersRepository {
    override fun getAllUsersStream(): Flow<List<User>> {
        return userDao.getAllUser()
    }

    override fun getUserStream(id: Int): Flow<User?> {
        return userDao.getUser(id)
    }

    override suspend fun insertUser(ind: User) {
        return userDao.insert(ind)
    }

    override suspend fun deleteUser(ind: User) {
        return userDao.delete(ind)
    }

    override suspend fun updateUser(ind: User) {
        return userDao.update(ind)
    }
}