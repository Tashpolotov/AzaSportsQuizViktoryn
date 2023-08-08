package com.example.azasportsquizviktoryn.ui.fragments

import android.os.Bundle
import android.os.Handler
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
    private val handler = Handler()
    private var correctAnswersCount = 0
    private var isLevel2Opened = false


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

                    if (currentQuestionIndex in questionsForSport.indices) {
                        currentQuestion = questionsForSport[currentQuestionIndex]
                        binding.tvQuestions1.text = currentQuestion.question
                        binding.tvAnswer1.text = currentQuestion.answers[0]
                        binding.tvAnswer2.text = currentQuestion.answers[1]
                        binding.tvAnswer3.text = currentQuestion.answers[2]

                        binding.cardAnswer1.setOnClickListener { onAnswerSelected(0) }
                        binding.cardAnswer2.setOnClickListener { onAnswerSelected(1) }
                        binding.cardAnswer3.setOnClickListener { onAnswerSelected(2) }
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
            Toast.makeText(requireContext(), "Правильно!", Toast.LENGTH_SHORT).show()
            correctAnswersCount++
        } else {
            Toast.makeText(requireContext(), "Неправильно!", Toast.LENGTH_SHORT).show()
            allAnswersCorrect = false
        }
        binding.cardAnswer1.isEnabled = false
        binding.cardAnswer2.isEnabled = false
        binding.cardAnswer3.isEnabled = false

        if (currentQuestionIndex == viewModel.quizSport.value.questions.size - 1) {
            if (allAnswersCorrect) {
                openSecondLevel()
            } else {
                binding.cardAnswer1.isEnabled = false
                binding.cardAnswer2.isEnabled = false
                binding.cardAnswer3.isEnabled = false
                Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
            }
        }
        handler.postDelayed({
            moveToNextQuestion()
        }, 2000)
    }
    private fun checkIfAllCorrectAnswers(): Boolean {
        val questionsForSport = viewModel.quizSport.value.questions
        return correctAnswersCount == questionsForSport.size
    }
    private fun openSecondLevel() {
        if (isLevel2Opened) {
            return
        }
        if (checkIfAllCorrectAnswers()) {
            Toast.makeText(requireContext(), "Поздравляем! Вы перешли на второй уровень!", Toast.LENGTH_LONG).show()
            val sportName = arguments?.getString("selected")
            viewModel.loadQuestions(sportName.toString(), level = "2")
            currentQuestionIndex = 0
            val questionsForSport = viewModel.quizSport.value.questions
            if (currentQuestionIndex in questionsForSport.indices) {
                currentQuestion = questionsForSport[currentQuestionIndex]
                binding.tvQuestions1.text = currentQuestion.question
                binding.tvAnswer1.text = currentQuestion.answers[0]
                binding.tvAnswer2.text = currentQuestion.answers[1]
                binding.tvAnswer3.text = currentQuestion.answers[2]
            }
            allAnswersCorrect = true
            binding.cardAnswer1.isEnabled = true
            binding.cardAnswer2.isEnabled = true
            binding.cardAnswer3.isEnabled = true

            answered = false
            isLevel2Opened = true
        } else {
            binding.cardAnswer1.isEnabled = false
            binding.cardAnswer2.isEnabled = false
            binding.cardAnswer3.isEnabled = false
            Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }
    private fun moveToNextQuestion() {
        // Разблокируем кнопки перед показом следующего вопроса
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
        } else {
            binding.cardAnswer1.isEnabled = false
            binding.cardAnswer2.isEnabled = false
            binding.cardAnswer3.isEnabled = false
            Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }
}