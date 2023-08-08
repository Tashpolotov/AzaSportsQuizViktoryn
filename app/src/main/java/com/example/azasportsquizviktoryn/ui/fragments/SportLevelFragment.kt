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

    }

    private fun initView(name: String) {
        val adapter =
            SportLevelAdapter(this::onItemClick) // Передаем номер последнего открытого уровня
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
        // Проверяем, правильно ли пользователь ответил на все вопросы 1 уровня
        val allQuestions = viewModel.quizSport.value.questions
        val isAllAnswersCorrect = allQuestions.all { question ->
            question.correctAnswer == question.correctAnswer
        }

        if (isAllAnswersCorrect) {
            // Если все ответы правильные, загружаем следующий уровень
            val nextLevel = selectedLevel.level + 1
            val sportQuestionFragment = SportQuestionFragment()
            val args = Bundle().apply {
                putString("selected", selectedLevel.sportCategory) // Передаем имя следующего уровня
                putInt("level", nextLevel) // Передаем номер следующего уровня
            }
            sportQuestionFragment.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.fr_container, sportQuestionFragment)
                .addToBackStack(null)
                .commit()
        } else {
            // Если есть неправильные ответы, показываем сообщение об ошибке или что-то другое
            Toast.makeText(
                requireContext(),
                "Ответьте правильно на все вопросы",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
