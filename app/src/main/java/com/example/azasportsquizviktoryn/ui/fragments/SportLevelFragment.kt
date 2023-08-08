package com.example.azasportsquizviktoryn.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.FragmentSportLevelBinding
import com.example.azasportsquizviktoryn.ui.adapter.SportLevelAdapter
import com.example.azasportsquizviktoryn.viewmodel.SportsQuizViewModel
import com.example.domain.model.LevelModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportLevelFragment : Fragment() {

    // Поля для хранения данных
    private lateinit var binding: FragmentSportLevelBinding
    private val viewModel by viewModels<SportsQuizViewModel>()
    private var currentLevel = 1 // Переменная для хранения текущего уровня

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение данных из аргументов
        val sportName = arguments?.getString("sportName")
        val sportImage = arguments?.getInt("sportImage")

        // Установка данных в интерфейс
        if (sportImage != null) {
            binding.imgSport.setImageResource(sportImage)
        }
        if (sportName != null) {
            binding.tvNameSport.text = sportName
            initView(sportName)
        }
    }

    private fun initView(name: String) {
        // Устанавливаем текущий уровень в соответствии с последним открытым уровнем
        currentLevel = viewModel.quizSport.value.lastUnlockedLevel + 1

        // Инициализация адаптера
        val adapter = SportLevelAdapter(this::onItemClick)
        binding.rv.adapter = adapter

        // Загрузка данных об уровнях
        lifecycleScope.launchWhenCreated {
            viewModel.quizSport.collect { sportQuizModel ->
                sportQuizModel?.let {
                    adapter.updateProgress(it.lastUnlockedLevel) // Обновляем прогресс уровней
                    adapter.submitList(it.level)
                }
            }
        }
        viewModel.loadSportLevel(name)
    }

    private fun onItemClick(selectedLevel: LevelModel) {
        val lastUnlockedLevel = viewModel.quizSport.value.lastUnlockedLevel

        if (selectedLevel.level <= lastUnlockedLevel + 1) {
            // Уровень открыт, загружаем следующий уровень
            currentLevel = selectedLevel.level // Устанавливаем текущий уровень на выбранный уровень
            val sportQuestionFragment = SportQuestionFragment()
            val args = Bundle().apply {
                putString("selected", selectedLevel.sportCategory) // Передаем имя спорта
                putInt("level", currentLevel) // Передаем текущий уровень
            }
            sportQuestionFragment.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.fr_container, sportQuestionFragment)
                .addToBackStack(null)
                .commit()
        } else {
            // Уровень закрыт, показываем сообщение об ошибке или что-то другое
            Toast.makeText(
                requireContext(),
                "Завершите предыдущие уровни, чтобы открыть этот уровень.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}