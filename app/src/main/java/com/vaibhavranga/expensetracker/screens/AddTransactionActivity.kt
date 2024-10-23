package com.vaibhavranga.expensetracker.screens

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vaibhavranga.expensetracker.R
import com.vaibhavranga.expensetracker.databinding.ActivityAddTransactionBinding
import com.vaibhavranga.expensetracker.databinding.DialogCatTypeBinding
import com.vaibhavranga.expensetracker.model.ExpenseModel
import com.vaibhavranga.expensetracker.recyclerviewadapter.CatTypeRecyclerViewAdapter
import com.vaibhavranga.expensetracker.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    private var selectedCatType = 0
    var selectedTransactionType = 0
    private lateinit var catTypeDialog: Dialog
    private lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        val viewModel by viewModels<DashboardViewModel>()

        val arrTransactionType = ArrayList<String>().apply {
            add("Debit")
            add("Credit")
        }
        binding.spinnerTranType.adapter = ArrayAdapter(this@AddTransactionActivity, android.R.layout.simple_spinner_dropdown_item, arrTransactionType)

        binding.spinnerTranType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTransactionType = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        myCalendar = Calendar.getInstance()
        val transactionDate = myCalendar
        var dateFormat = SimpleDateFormat("dd MMMM yyyy").format(transactionDate.timeInMillis)
        binding.textViewTransactionDate.text = dateFormat

        binding.linearLayoutTransactionDate.setOnClickListener {
            myCalendar = Calendar.getInstance()
            val year = myCalendar.get(Calendar.YEAR)
            val month = myCalendar.get(Calendar.MONTH)
            val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this@AddTransactionActivity, object : OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    transactionDate.set(Calendar.YEAR, year)
                    transactionDate.set(Calendar.MONTH, month)
                    transactionDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    dateFormat = SimpleDateFormat("dd MMMM yyyy").format(transactionDate.timeInMillis)
                    binding.textViewTransactionDate.text = dateFormat
                }
            }, year, month, dayOfMonth).show()
        }

        binding.linearLayoutSelectCategoryType.setOnClickListener {
            catTypeDialog = Dialog(this@AddTransactionActivity)

            val dialogBinding = DialogCatTypeBinding.inflate(layoutInflater)
            catTypeDialog.setContentView(dialogBinding.root)

            dialogBinding.recyclerViewCatType.layoutManager = GridLayoutManager(this@AddTransactionActivity, 3)
            dialogBinding.recyclerViewCatType.adapter = CatTypeRecyclerViewAdapter(this@AddTransactionActivity, MainActivity.arrCat)

            catTypeDialog.show()
        }

        binding.btnAddTransaction.setOnClickListener {
            if (binding.edtTitle.text.toString() != ""
                && binding.edtDesc.text.toString() != ""
                && binding.edtAmount.text.toString() != "") {
                var mainBalance = intent.getDoubleExtra(MainActivity.KEY_BALANCE, 0.0)
                if (selectedTransactionType == 0) {
                    mainBalance -= binding.edtAmount.text.toString().toDouble()
                } else {
                    mainBalance += binding.edtAmount.text.toString().toDouble()
                }
                val expense = ExpenseModel(
                    0,
                    binding.edtTitle.text.toString(),
                    binding.edtDesc.text.toString(),
                    binding.edtAmount.text.toString().toDouble(),
                    mainBalance,
                    selectedTransactionType,
                    selectedCatType,
                    transactionDate.timeInMillis.toString()
                )
                viewModel.addExpense(expense)
                finish()
            } else {
                Toast.makeText(this@AddTransactionActivity, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onCatTypeSelected(id: Int) {
        selectedCatType = id
        val category = MainActivity.arrCat[selectedCatType]
        binding.textViewCategoryType.text = category.name
        binding.imageViewCategoryType.setImageResource(category.imagePath)
        catTypeDialog.dismiss()
    }
}