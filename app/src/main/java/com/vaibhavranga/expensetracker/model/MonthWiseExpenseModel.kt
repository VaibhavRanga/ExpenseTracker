package com.vaibhavranga.expensetracker.model

data class MonthWiseExpenseModel(
    val month: String,
    val amount: Double,
    val transactions: ArrayList<ExpenseModel>
)
