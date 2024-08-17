package com.example.currencyrateapp.repository

import com.example.currencyrateapp.database.AppDatabase
import com.example.currencyrateapp.database.User

class UserRepository(private val appDatabase: AppDatabase) {

    private val userDao = appDatabase.userDao()

    suspend fun saveUser(user: User) {
        userDao.deleteAll()
        userDao.insert(user)
    }

    suspend fun getUser(): User? {
        return userDao.getUser()
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
}
