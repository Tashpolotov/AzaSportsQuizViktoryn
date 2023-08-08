package com.example.azasportsquizviktoryn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.ItemLevelBinding
import com.example.domain.model.LevelModel


class SportLevelAdapter(private val onItemClick: (LevelModel) -> Unit) :
    ListAdapter<LevelModel, SportLevelAdapter.SportLevelHolder>(SportsLevelDiffCallback()) {

    private var lastUnlockedLevel: Int = 1 // Переменная для отслеживания последнего открытого уровня

    fun updateProgress(unlockedLevel: Int) {
        lastUnlockedLevel = unlockedLevel
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportLevelHolder {
        return SportLevelHolder(ItemLevelBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: SportLevelHolder, position: Int) {
        val level = getItem(position)
        holder.bind(level, lastUnlockedLevel)
        holder.itemView.setOnClickListener {
            onItemClick(level)
        }
    }

    inner class SportLevelHolder(val binding: ItemLevelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: LevelModel, lastUnlockedLevel: Int) {
            binding.tvLevelOne.text = "Уровень: ${model.level}"
            // Проверяем, является ли текущий уровень последним открытым уровнем или меньше него
            if (model.level <= lastUnlockedLevel) {
                // Уровень открыт, показываем зеленую галочку и делаем его кликабельным
                binding.imageArrowLevel.setImageResource(R.drawable.ic_chek)
                binding.root.isEnabled = true
            } else {
                // Уровень закрыт, показываем замок и делаем его некликабельным
                binding.imageArrowLevel.setImageResource(R.drawable.ic_lock)
                binding.root.isEnabled = false
            }
        }
    }

    private class SportsLevelDiffCallback : DiffUtil.ItemCallback<LevelModel>() {
        override fun areItemsTheSame(oldItem: LevelModel, newItem: LevelModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LevelModel, newItem: LevelModel): Boolean {
            return oldItem == newItem
        }
    }
}