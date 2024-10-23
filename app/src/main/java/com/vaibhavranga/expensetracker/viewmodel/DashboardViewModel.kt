package com.vaibhavranga.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavranga.expensetracker.model.ExpenseModel
import com.vaibhavranga.expensetracker.repo.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel() {

    fun fetchAllExpenses() = expenseRepository.fetchAllExpenses()

    fun addExpense(newExpense: ExpenseModel) = viewModelScope.launch {expenseRepository.addExpense(newExpense)}

    fun updateExpense(expense: ExpenseModel) = viewModelScope.launch {expenseRepository.updateExpense(expense)}

    fun deleteExpense(expense: ExpenseModel) = viewModelScope.launch {expenseRepository.deleteExpense(expense)}
}