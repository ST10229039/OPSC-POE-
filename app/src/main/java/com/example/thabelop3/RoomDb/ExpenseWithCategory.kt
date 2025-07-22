package com.example.thabelop3.RoomDb

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class ExpenseWithCategory(
    @Embedded val expense: Expense,
    @ColumnInfo(name = "categoryName") val categoryName: String?

)