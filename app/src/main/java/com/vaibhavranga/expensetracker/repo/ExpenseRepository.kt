package com.vaibhavranga.expensetracker.repo

import com.vaibhavranga.expensetracker.model.ExpenseModel
import com.vaibhavranga.expensetracker.roomdatabase.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    fun fetchAllExpenses() = expenseDao.fetchAllExpense()

    suspend fun addExpense(newExpense: ExpenseModel) = expenseDao.addExpense(newExpense)

    suspend fun updateExpense(expense: ExpenseModel) = expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: ExpenseModel) = expenseDao.deleteExpense(expense)
}