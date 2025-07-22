package com.example.thabelop3.Models

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thabelop3.RoomDb.AppDatabase
import com.example.thabelop3.RoomDb.Expense
import kotlinx.coroutines.launch
import kotlin.text.insert

/**
 * ViewModel for Expense operations
 * Provides data to UI and survives configuration changes
 */
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        // Initialize repository with ExpenseDao from database
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }
    // Insert a new expense in background thread
    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }
    // Get all expenses for a specific user as LiveData
    fun getExpensesByUser(userId: Int) = liveData {
        emit(repository.getExpensesByUser(userId))
    }
    // Get expenses within a date range for a specific user
    fun getExpensesByDateRange(userId: Int, startDate: Long, endDate: Long) = liveData {
        emit(repository.getExpensesByDateRange(userId, startDate, endDate))
    }
    // Get total amount spent in a category within date range
    fun getCategoryTotal(userId: Int, categoryId: Int, startDate: Long, endDate: Long) = liveData {
        emit(repository.getCategoryTotal(userId, categoryId, startDate, endDate))
    }
    // Delete an expense in background thread
    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
    // In ExpenseViewModel.kt, add these functions:
    private val sharedPrefs by lazy {
        getApplication<Application>().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    fun getMonthlyBudget(userId: Int): Float {
        return sharedPrefs.getFloat("monthly_budget_$userId", 0f)
    }

    fun setMonthlyBudget(userId: Int, amount: Float) {
        sharedPrefs.edit().putFloat("monthly_budget_$userId", amount).apply()
    }
    // In ExpenseViewModel.kt
    fun getExpensesWithCategoryByUser(userId: Int) = liveData {
        emit(repository.getExpensesWithCategoryByUser(userId))
    }
    fun getExpensesWithCategoryByDateRange(userId: Int, startDate: Long, endDate: Long) = liveData {
        emit(repository.getExpensesWithCategoryByDateRange(userId, startDate, endDate))
    }
}