package com.madlab.medicinedb

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import com.madlab.medicinedb.databinding.FragmentItemBinding
import com.madlab.medicinedb.db.MedicineModel

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyMedicineRecyclerViewAdapter(
    private var values: List<MedicineModel> = emptyList(),
    private val onClick: (med: MedicineModel) -> Unit = {}
) : RecyclerView.Adapter<MyMedicineRecyclerViewAdapter.ViewHolder>() {

    fun update(newValues: List<MedicineModel>){
        val oldValues = values
        values = newValues
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldValues.size

            override fun getNewListSize() = newValues.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldValues[oldItemPosition].id == newValues[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldValues[oldItemPosition] == newValues[newItemPosition]
            }

        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.root.setOnClickListener {
            onClick(item)
        }
        holder.binding.med = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root)
}