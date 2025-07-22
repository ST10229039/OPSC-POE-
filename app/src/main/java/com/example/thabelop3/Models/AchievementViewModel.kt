package com.example.thabelop3.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thabelop3.RoomDb.Achievement
import com.example.thabelop3.RoomDb.AppDatabase
import kotlinx.coroutines.launch
import kotlin.text.insert

class AchievementViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AchievementRepository

    init {
        val achievementDao = AppDatabase.getDatabase(application).achievementDao()
        repository = AchievementRepository(achievementDao)
    }

    fun insert(achievement: Achievement) = viewModelScope.launch {
        repository.insert(achievement)
    }

    fun getAchievementsByUser(userId: Int) = liveData {
        emit(repository.getAchievementsByUser(userId))
    }

    fun getAchievementByType(userId: Int, type: String) = liveData {
        emit(repository.getAchievementByType(userId, type))
    }
}