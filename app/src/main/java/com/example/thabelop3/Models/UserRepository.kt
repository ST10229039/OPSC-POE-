package com.example.thabelop3.Models

import com.example.thabelop3.RoomDb.User
import com.example.thabelop3.RoomDb.UserDao

/**
 * Repository for User operations
 * Abstracts data sources from ViewModel
 */

class UserRepository(private val userDao: UserDao) {
    // Insert a new user
    suspend fun insert(user: User) = userDao.insert(user)

    // Get user by credentials



    // Get user by credentials
    suspend fun checkUsernameExists(username: String) = userDao.checkUsernameExists(username)

    suspend fun getUserByUsernameOrEmail(usernameOrEmail: String, password: String) =
        userDao.getUserByUsernameOrEmail(usernameOrEmail, password)

    suspend fun checkEmailExists(email: String) = userDao.checkEmailExists(email)



}