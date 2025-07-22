package com.example.thabelop3.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Add AchievementDao
@Dao
interface AchievementDao {
    @Insert
    suspend fun insert(achievement: Achievement)

    @Query("SELECT * FROM achievements WHERE user_id = :userId")
    suspend fun getAchievementsByUser(userId: Int): List<Achievement>

    @Query("SELECT * FROM achievements WHERE user_id = :userId AND type = :type")
    suspend fun getAchievementByType(userId: Int, type: String): Achievement?

}