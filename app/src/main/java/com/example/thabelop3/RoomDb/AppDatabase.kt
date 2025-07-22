package com.example.thabelop3.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// In AppDatabase.kt - add migration handling
@Database(
    entities = [User::class, Category::class, Expense::class, BudgetGoal::class ,  Achievement::class],
    version = 2, // Incremented version
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao// DAO for User entity operations
    abstract fun categoryDao(): CategoryDao// DAO for Category entity operations
    abstract fun expenseDao(): ExpenseDao  // DAO for Expense entity operations
    abstract fun budgetGoalDao(): BudgetGoalDao // DAO for BudgetGoal entity operations
    abstract fun achievementDao(): AchievementDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gmn18_db"
                )
                    .addMigrations(MIGRATION_1_2) // Add migration
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add any schema changes here if needed
                database.execSQL("ALTER TABLE users ADD COLUMN email TEXT NOT NULL DEFAULT ''")
            }
        }

    }
}