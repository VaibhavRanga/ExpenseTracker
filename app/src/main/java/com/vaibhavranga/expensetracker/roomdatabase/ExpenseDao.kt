package com.vaibhavranga.expensetracker.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vaibhavranga.expensetracker.model.ExpenseModel

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense_table")
    fun fetchAllExpense(): LiveData<List<ExpenseModel>>

    @Insert
    suspend fun addExpense(newExpense: ExpenseModel)

    @Update
    suspend fun updateExpense(expense: ExpenseModel)

    @Delete
    suspend fun deleteExpense(expense: ExpenseModel)
}