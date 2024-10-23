package com.vaibhavranga.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.vaibhavranga.expensetracker.roomdatabase.AppDatabase
import com.vaibhavranga.expensetracker.roomdatabase.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun providesRoomDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "expense_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }
}