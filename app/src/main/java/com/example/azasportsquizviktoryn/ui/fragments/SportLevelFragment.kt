package com.example.azasportsquizviktoryn.ui.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
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

    private lateinit var binding: FragmentSportLevelBinding
    private val viewModel by viewModels<SportsQuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sportName = arguments?.getString("sportName")
        val sportImage = arguments?.getInt("sportImage")

        if (sportImage != null) {
            binding.imgSport.setImageResource(sportImage)
        }
        if (sportName != null) {
            binding.tvNameSport.text = sportName
            initView(sportName)
        }

        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initView(name: String) {
        // Загрузите последний открытый уровень
        val lastUnlockedLevel = getLastUnlockedLevelFromRepository()

        // Передайте список пройденных уровней и последний открытый уровень в адаптер
        val adapter = SportLevelAdapter(this::onItemClick, viewModel.completedLevels.value.toSet())
        adapter.updateProgress(lastUnlockedLevel)
        binding.rv.adapter = adapter

        lifecycleScope.launchWhenCreated {
            viewModel.quizSport.collect {
                adapter.submitList(it.level)
            }
        }
        viewModel.loadSportLevel(name)

        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun onItemClick(selectedLevel: LevelModel) {
        // Проверяем, является ли текущий уровень последним открытым уровнем или меньше него
        val lastUnlockedLevel = viewModel.getLastUnlockedLevel()
        if (selectedLevel.level <= lastUnlockedLevel) {
            // Уровень открыт, переходим к выбранному уровню
            val sportQuestionFragment = SportQuestionFragment()
            val args = Bundle().apply {
                putString("selected", selectedLevel.sportCategory) // Передаем имя спорта
                putInt("level", selectedLevel.level) // Передаем номер уровня
            }
            sportQuestionFragment.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.fr_container, sportQuestionFragment)
                .addToBackStack(null)
                .commit()

            // Здесь вызываем метод для обновления пройденных уровней
            viewModel.updateCompletedLevel(selectedLevel.level)
        } else {
            // Уровень закрыт, показываем сообщение об ошибке или что-то другое
            Toast.makeText(
                requireContext(),
                "Ответьте правильно на все вопросы предыдущих уровней",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Вместо этой заглушки необходимо реализовать получение последнего открытого уровня из репозитория
    private fun getLastUnlockedLevelFromRepository(): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        return sharedPreferences.getInt("last_unlocked_level", 1)
    }
}