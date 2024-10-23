package com.vaibhavranga.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "expense_id") val id: Int,
    @ColumnInfo(name = "expense_title") val title: String,
    @ColumnInfo(name = "expense_desc") val desc: String,
    @ColumnInfo(name = "expense_amount") val amount: Double,
    @ColumnInfo(name = "expense_balance") val balance: Double,
    @ColumnInfo(name = "expense_type") val type: Int,
    @ColumnInfo(name = "expense_cat_type") val catType: Int,
    @ColumnInfo(name = "expense_date") val date: String,
)
