package com.vaibhavranga.expensetracker.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vaibhavranga.expensetracker.databinding.TranCatTypeListItemBinding
import com.vaibhavranga.expensetracker.model.CategoryModel
import com.vaibhavranga.expensetracker.screens.AddTransactionActivity

class CatTypeRecyclerViewAdapter(
    private val context: Context,
    val arrCatTypes: ArrayList<CategoryModel>
) : RecyclerView.Adapter<CatTypeRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: TranCatTypeListItemBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TranCatTypeListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrCatTypes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val arrCatType = arrCatTypes[position]

        holder.binding.imgCatType.setImageResource(arrCatType.imagePath)
        holder.binding.txtCatType.text = arrCatType.name

        holder.binding.root.setOnClickListener {
            (context as AddTransactionActivity).onCatTypeSelected(arrCatType.id)
        }
    }
}