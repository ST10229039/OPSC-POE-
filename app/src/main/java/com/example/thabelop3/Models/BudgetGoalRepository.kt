package com.example.thabelop3.Models

import com.example.thabelop3.RoomDb.BudgetGoal
import com.example.thabelop3.RoomDb.BudgetGoalDao

/**
 * Repository for BudgetGoal operations
 * Mediates between ViewModel and data source
 */
class BudgetGoalRepository(private val budgetGoalDao: BudgetGoalDao) {
    // Insert new budget goal
    suspend fun insert(goal: BudgetGoal) = budgetGoalDao.insert(goal)
    // Get budget goal for specific user, month and year
    suspend fun getBudgetGoal(userId: Int, month: Int, year: Int) =
        budgetGoalDao.getBudgetGoal(userId, month, year)
    // Update existing budget goal
    suspend fun update(goal: BudgetGoal) = budgetGoalDao.update(goal)
}