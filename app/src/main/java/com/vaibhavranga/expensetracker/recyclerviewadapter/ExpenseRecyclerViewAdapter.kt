package com.vaibhavranga.expensetracker.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vaibhavranga.expensetracker.R
import com.vaibhavranga.expensetracker.databinding.ExpenseListItemBinding
import com.vaibhavranga.expensetracker.model.ExpenseModel
import com.vaibhavranga.expensetracker.screens.MainActivity

class ExpenseRecyclerViewAdapter(private val context: Context, private val arrExpense: List<ExpenseModel>, val editExpense: (ExpenseModel) -> Unit, val deleteExpense: (ExpenseModel) -> Unit) : RecyclerView.Adapter<ExpenseRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ExpenseListItemBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ExpenseListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrExpense.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expense = arrExpense[position]

        holder.binding.textViewTitle.text = expense.title
        holder.binding.textViewDesc.text = expense.desc
        holder.binding.textViewAmount.text = expense.amount.toString()
        holder.binding.textViewBalance.text = expense.balance.toString()
        holder.binding.imageViewCategoryType.setImageResource(MainActivity.arrCat[expense.catType].imagePath)

        if (expense.type == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.red, null))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.green, null))
        }
        holder.binding.root.setOnClickListener {
            editExpense(expense)
        }
        holder.binding.root.setOnLongClickListener {
            deleteExpense(expense)
            true
        }
    }
}