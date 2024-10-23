package com.vaibhavranga.expensetracker.screens

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vaibhavranga.expensetracker.R
import com.vaibhavranga.expensetracker.databinding.FragmentDashboardBinding
import com.vaibhavranga.expensetracker.model.ExpenseModel
import com.vaibhavranga.expensetracker.model.MonthWiseExpenseModel
import com.vaibhavranga.expensetracker.recyclerviewadapter.ExpenseRecyclerViewAdapter
import com.vaibhavranga.expensetracker.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var mainBalance = 0.0
    private lateinit var arrMonths: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.materialToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.toolbar_add) {
                startActivity(
                    Intent(context, AddTransactionActivity::class.java).putExtra(
                    MainActivity.KEY_BALANCE, mainBalance))
            }
            true
        }

        arrMonths = listOf(
            "Jan",
            "Fab",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )

        dashboardViewModel.fetchAllExpenses().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.groupWithoutTransasctions.visibility = View.GONE
                binding.groupWithTransactions.visibility = View.VISIBLE
                mainBalance = it[it.size - 1].balance
                binding.textViewAmount.text = "$mainBalance"
                getMonthWiseExpense(it as ArrayList<ExpenseModel>)
                binding.recyclerViewExpense.layoutManager =
                    LinearLayoutManager(context)
                val expenseRecyclerViewAdapter = ExpenseRecyclerViewAdapter(
                    requireContext(),
                    it,
                    {

                    }
                ) { expense ->
                    deleteTransaction(expense)
                }
                binding.recyclerViewExpense.adapter = expenseRecyclerViewAdapter
            } else {
                binding.groupWithTransactions.visibility = View.GONE
                binding.groupWithoutTransasctions.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    private fun deleteTransaction(expense: ExpenseModel) {
        val deleteDialog = MaterialAlertDialogBuilder(requireContext())

        deleteDialog.setTitle("Delete ${expense.title}")

        deleteDialog.setPositiveButton("Yes") { dialog, which ->
            dashboardViewModel.deleteExpense(expense)
        }
        deleteDialog.setNegativeButton("No") { dialog, which ->

        }
        deleteDialog.show()
    }

    private fun getMonthWiseExpense(expenses: ArrayList<ExpenseModel>) {
        val arrMonthWiseExpenses = ArrayList<MonthWiseExpenseModel>()
        val uniqueMonths = ArrayList<Int>()

        for (expense in expenses) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = expense.date.toLong()
            val expenseMonth = cal.get(Calendar.MONTH)
            if (!uniqueMonths.contains(expenseMonth)) {
                uniqueMonths.add(expenseMonth)
            }
        }
        for (eachMonth in uniqueMonths) {
            val eachMonthExpense = ArrayList<ExpenseModel>()

            for (expense in expenses) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = expense.date.toLong()
                val expenseMonth = cal.get(Calendar.MONTH)

                if (eachMonth == expenseMonth) {
                    eachMonthExpense.add(expense)
                }
            }
            var eachMonthAmount = 0.0

            for (expense in eachMonthExpense) {
                if (expense.type == 0) {
                    eachMonthAmount -= expense.amount
                } else {
                    eachMonthAmount += expense.amount
                }
            }
            arrMonthWiseExpenses.add(MonthWiseExpenseModel(arrMonths[eachMonth], eachMonthAmount, eachMonthExpense))
        }
    }
}