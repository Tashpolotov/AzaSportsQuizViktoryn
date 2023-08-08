package com.example.azasportsquizviktoryn.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.azasportsquizviktoryn.databinding.FragmentSportQuestionBinding
import com.example.azasportsquizviktoryn.viewmodel.SportsQuizViewModel
import com.example.domain.model.SportQuestionModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportQuestionFragment : Fragment() {

    private lateinit var binding: FragmentSportQuestionBinding
    private val viewModel by viewModels<SportsQuizViewModel>()
    private var currentQuestionIndex = 0
    private lateinit var currentQuestion: SportQuestionModel
    private var allAnswersCorrect = true
    private var answered = false
    private var correctAnswersCount = 0
    private var currentQuestionCounter = 0
    private var currentLevelCounter = 1
    private var totalLevels = 0
    private var totalQuestionsInCurrentLevel = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sportName = arguments?.getString("selected")

        viewModel.loadQuestions(sportName.toString(), level = "1")

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.quizSport.collect { sportQuizModel ->
                sportQuizModel?.let {
                    val questionsForSport = it.questions

                    totalLevels = questionsForSport.lastOrNull()?.level?.toInt() ?: 0
                    totalQuestionsInCurrentLevel = questionsForSport.count { question -> question.level.toInt() == currentLevelCounter }

                    if (currentQuestionIndex in questionsForSport.indices) {
                        currentQuestion = questionsForSport[currentQuestionIndex]
                        binding.tvQuestions1.text = currentQuestion.question
                        binding.tvAnswer1.text = currentQuestion.answers[0]
                        binding.tvAnswer2.text = currentQuestion.answers[1]
                        binding.tvAnswer3.text = currentQuestion.answers[2]

                        binding.cardAnswer1.setOnClickListener { onAnswerSelected(0) }
                        binding.cardAnswer2.setOnClickListener { onAnswerSelected(1) }
                        binding.cardAnswer3.setOnClickListener { onAnswerSelected(2) }

                        currentQuestionCounter++
                        updateCounters()
                    }
                }
            }
        }
    }

    private fun onAnswerSelected(selectedAnswer: Int) {
        if (answered) {
            return
        }
        answered = true
        if (selectedAnswer == currentQuestion.correctAnswer) {
            Log.e("SportQuestionFragment", "Правильный ответ")
            Toast.makeText(requireContext(), "Правильно!", Toast.LENGTH_SHORT).show()
            correctAnswersCount++
        } else {
            Log.e("SportQuestionFragment", "Неправильный ответ")
            Toast.makeText(requireContext(), "Неправильно!", Toast.LENGTH_SHORT).show()
            allAnswersCorrect = false
        }
        binding.cardAnswer1.isEnabled = false
        binding.cardAnswer2.isEnabled = false
        binding.cardAnswer3.isEnabled = false

        if (currentQuestionIndex == viewModel.quizSport.value.questions.size - 1) {
            if (allAnswersCorrect) {
                Log.e("SportQuestionFragment", "Все ответы правильны. Открываем следующий уровень.")
                openNextLevel()
            } else {
                Log.e("SportQuestionFragment", "Вопросы закончились. Не все ответы правильны.")
                binding.cardAnswer1.isEnabled = false
                binding.cardAnswer2.isEnabled = false
                binding.cardAnswer3.isEnabled = false
                Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("SportQuestionFragment", "Переходим к следующему вопросу.")
            moveToNextQuestion()
        }
    }

    private fun openNextLevel() {
        Log.d("SportQuestionFragment", "Открываем следующий уровень")
        val sportName = arguments?.getString("selected")
        val currentLevel = currentQuestion.level.toInt()
        val nextLevel = currentLevel + 1
        viewModel.updateCompletedLevel(nextLevel)

        viewModel.loadQuestions(sportName.toString(), level = nextLevel.toString())

        currentQuestionIndex = viewModel.quizSport.value.questions.indexOfFirst { it.level == nextLevel.toString() }
        val questionsForSport = viewModel.quizSport.value.questions
        if (currentQuestionIndex in questionsForSport.indices) {
            currentQuestion = questionsForSport[currentQuestionIndex]
            binding.tvQuestions1.text = currentQuestion.question
            binding.tvAnswer1.text = currentQuestion.answers[0]
            binding.tvAnswer2.text = currentQuestion.answers[1]
            binding.tvAnswer3.text = currentQuestion.answers[2]
        }
        correctAnswersCount = 0
        allAnswersCorrect = true
        binding.cardAnswer1.isEnabled = true
        binding.cardAnswer2.isEnabled = true
        binding.cardAnswer3.isEnabled = true
        answered = false

        currentQuestionCounter = 1
        currentLevelCounter++
        totalQuestionsInCurrentLevel = questionsForSport.count { question -> question.level.toInt() == currentLevelCounter }
        updateCounters()

        showLevelCompleteMessage()
    }


    private fun moveToNextQuestion() {
        binding.cardAnswer1.isEnabled = true
        binding.cardAnswer2.isEnabled = true
        binding.cardAnswer3.isEnabled = true

        currentQuestionIndex++
        val questionsForSport = viewModel.quizSport.value.questions

        if (currentQuestionIndex < questionsForSport.size) {
            currentQuestion = questionsForSport[currentQuestionIndex]
            binding.tvQuestions1.text = currentQuestion.question
            binding.tvAnswer1.text = currentQuestion.answers[0]
            binding.tvAnswer2.text = currentQuestion.answers[1]
            binding.tvAnswer3.text = currentQuestion.answers[2]

            allAnswersCorrect = true
            answered = false

            currentQuestionCounter++
            updateCounters()
        } else {
            binding.cardAnswer1.isEnabled = false
            binding.cardAnswer2.isEnabled = false
            binding.cardAnswer3.isEnabled = false
            showLevelCompleteMessage()
        }
    }

    private fun showLevelCompleteMessage() {
        Toast.makeText(requireContext(), "Вопросы уровня пройдены. " +
                "Переход на следующий уровень", Toast.LENGTH_SHORT).show()
    }

    private fun updateCounters() {
        binding.tvScoreQuestions.text = "$currentQuestionCounter/$totalQuestionsInCurrentLevel"
        binding.tvScoreLevel.text = "$currentLevelCounter"
    }
}