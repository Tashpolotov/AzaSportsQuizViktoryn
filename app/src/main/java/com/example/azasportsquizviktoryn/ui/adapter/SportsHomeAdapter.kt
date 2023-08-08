package com.example.azasportsquizviktoryn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.azasportsquizviktoryn.databinding.ItemHomeBinding
import com.example.domain.model.SportsModel

class SportsHomeAdapter(private val onItemClick: (SportsModel) -> Unit) : ListAdapter<SportsModel, SportsHomeAdapter.SportsViewHolder>(SportsModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        return SportsViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SportsViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SportsModel) {
            binding.tvCategoryNameMain.text = model.name
            Glide.with(binding.root)
                .load(model.image)
                .into(binding.imgSport)

            itemView.setOnClickListener {
                onItemClick(model)
            }
        }
    }

    private class SportsModelDiffCallback : DiffUtil.ItemCallback<SportsModel>() {
        override fun areItemsTheSame(oldItem: SportsModel, newItem: SportsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SportsModel, newItem: SportsModel): Boolean {
            return oldItem == newItem
        }
    }
}