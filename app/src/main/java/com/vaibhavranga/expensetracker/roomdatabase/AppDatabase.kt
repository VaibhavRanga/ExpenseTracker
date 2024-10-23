package com.vaibhavranga.expensetracker.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vaibhavranga.expensetracker.model.ExpenseModel

@Database(entities = [ExpenseModel::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
}