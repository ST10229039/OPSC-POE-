package com.example.thabelop3.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thabelop3.RoomDb.AppDatabase
import com.example.thabelop3.RoomDb.User
import kotlinx.coroutines.launch

/**
 * ViewModel for User operations
 * Manages user-related data for UI
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    // Initialize repository with UserDao from database
    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    // Insert new user in background thread
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
    // Get user by credentials as LiveData

    // Check username existence as LiveData
    fun checkUsernameExists(username: String) = liveData {
        emit(repository.checkUsernameExists(username))
    }
    // Check email existence as LiveData

    fun checkEmailExists(email: String) = liveData {
        emit(repository.checkEmailExists(email))
    }
    fun getUserByUsernameOrEmail(usernameOrEmail: String, password: String) = liveData {
        try {
            val user = repository.getUserByUsernameOrEmail(usernameOrEmail, password)
            emit(user)
        } catch (e: Exception) {
            // Handle error if needed
            emit(null)
        }
    }
}