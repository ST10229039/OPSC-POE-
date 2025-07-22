package com.example.thabelop3.Models

import com.example.thabelop3.RoomDb.Achievement
import com.example.thabelop3.RoomDb.AchievementDao


class AchievementRepository(private val achievementDao: AchievementDao) {
    suspend fun insert(achievement: Achievement) = achievementDao.insert(achievement)
    suspend fun getAchievementsByUser(userId: Int) = achievementDao.getAchievementsByUser(userId)
    suspend fun getAchievementByType(userId: Int, type: String) = achievementDao.getAchievementByType(userId, type)


}