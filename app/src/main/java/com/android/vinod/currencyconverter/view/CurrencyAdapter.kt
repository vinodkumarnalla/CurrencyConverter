package com.android.vinod.currencyconverter.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.currencyconverter.R
import com.android.vinod.currencyconverter.databinding.ItemLayoutBinding

class CurrencyAdapter(private val list: List<CurrencyModel>) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_layout, parent, false
        )
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: CurrencyModel = list[position]
        holder.bind(dataModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: CurrencyModel) {
            binding.model = obj
            binding.executePendingBindings()
        }
    }
}