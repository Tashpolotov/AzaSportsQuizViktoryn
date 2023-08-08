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
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.FragmentSportQuestionBinding
import com.example.azasportsquizviktoryn.viewmodel.SportsQuizViewModel
import com.example.domain.model.SportQuestionModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportQuestionFragment : Fragment() {

    // Объявляем переменные класса
    private lateinit var binding: FragmentSportQuestionBinding
    private val viewModel by viewModels<SportsQuizViewModel>()
    private var currentQuestionIndex = 0
    private lateinit var currentQuestion: SportQuestionModel
    private var allAnswersCorrect = true // Переменная для отслеживания правильности всех ответов
    private var answered = false // Флаг для отслеживания того, отвечал ли пользователь на текущий вопрос
    private val handler = Handler()
    private var correctAnswersCount = 0 // Переменная для отслеживания количества правильно ответленных вопросов
    private var isLevel2Opened = false // Флаг для отслеживания того, был ли выполнен переход на второй уровень

    // Переопределяем метод onCreateView для создания и настройки макета фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Переопределяем метод onViewCreated для выполнения действий после создания представления фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем имя спорта из аргументов фрагмента
        val sportName = arguments?.getString("selected")

        // Загружаем вопросы первого уровня спорта
        viewModel.loadQuestions(sportName.toString(), level = "1")

        // Назначаем слушатель нажатия на кнопку "Далее"

        // Наблюдаем за изменениями данных в ViewModel
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.quizSport.collect { sportQuizModel ->
                sportQuizModel?.let {
                    val questionsForSport = it.questions

                    // Проверяем, существует ли вопрос с текущим индексом
                    if (currentQuestionIndex in questionsForSport.indices) {
                        currentQuestion = questionsForSport[currentQuestionIndex]
                        binding.tvQuestions1.text = currentQuestion.question
                        binding.tvAnswer1.text = currentQuestion.answers[0]
                        binding.tvAnswer2.text = currentQuestion.answers[1]
                        binding.tvAnswer3.text = currentQuestion.answers[2]

                        // Устанавливаем слушатели кликов на варианты ответов
                        binding.cardAnswer1.setOnClickListener { onAnswerSelected(0) }
                        binding.cardAnswer2.setOnClickListener { onAnswerSelected(1) }
                        binding.cardAnswer3.setOnClickListener { onAnswerSelected(2) }
                    }
                }
            }
        }
    }

    // Метод для обработки выбора пользователем ответа
    private fun onAnswerSelected(selectedAnswer: Int) {
        // Проверяем, ответил ли пользователь уже на вопрос
        if (answered) {
            // Если пользователь уже ответил, ничего не делаем
            return
        }

        answered = true // Устанавливаем флаг, что пользователь ответил на вопрос

        // Проверяем правильность ответа и показываем результат
        if (selectedAnswer == currentQuestion.correctAnswer) {
            // Если ответ правильный, показываем Toast с сообщением "Правильно"
            Toast.makeText(requireContext(), "Правильно!", Toast.LENGTH_SHORT).show()

            // Увеличиваем счетчик правильных ответов
            correctAnswersCount++
        } else {
            // Если ответ неправильный, показываем Toast с сообщением "Неправильно"
            Toast.makeText(requireContext(), "Неправильно!", Toast.LENGTH_SHORT).show()
            // Если есть хотя бы один неправильный ответ, устанавливаем allAnswersCorrect в false
            allAnswersCorrect = false
        }

        // Блокируем кнопки после выбора ответа
        binding.cardAnswer1.isEnabled = false
        binding.cardAnswer2.isEnabled = false
        binding.cardAnswer3.isEnabled = false

        // Проверяем, является ли последний вопрос на первом уровне
        if (currentQuestionIndex == viewModel.quizSport.value.questions.size - 1) {
            // Если это последний вопрос на первом уровне, то проверяем правильность всех ответов
            if (allAnswersCorrect) {
                // Если все ответы правильные, переходим на второй уровень
                openSecondLevel()
            } else {
                // Если есть хотя бы один неправильный ответ, блокируем кнопки ответов
                binding.cardAnswer1.isEnabled = false
                binding.cardAnswer2.isEnabled = false
                binding.cardAnswer3.isEnabled = false
                // Показываем сообщение о том, что вопросы закончились
                Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
            }
        }

        // Задержка перед автоматическим переходом к следующему вопросу
        handler.postDelayed({
            // Переходим к следующему вопросу после задержки
            moveToNextQuestion()
        }, 2000) // Измените значение задержки здесь (в миллисекундах), если нужно изменить время задержки
    }

    // Метод для проверки, все ли вопросы на первом уровне были правильно отвечены
    private fun checkIfAllCorrectAnswers(): Boolean {
        // Проверяем, все ли вопросы на первом уровне были правильно отвечены
        val questionsForSport = viewModel.quizSport.value.questions
        return correctAnswersCount == questionsForSport.size
    }

    // Метод для открытия второго уровня
    private fun openSecondLevel() {
        // Проверяем, был ли уже выполнен переход на второй уровень
        if (isLevel2Opened) {
            return // Если уже перешли на второй уровень, ничего не делаем
        }

        // Проверяем, все ли вопросы на первом уровне были правильно отвечены
        if (checkIfAllCorrectAnswers()) {
            // Если все ответы правильные, то переходим на второй уровень
            Toast.makeText(requireContext(), "Поздравляем! Вы перешли на второй уровень!", Toast.LENGTH_LONG).show()

            // Загружаем вопросы второго уровня из ViewModel
            val sportName = arguments?.getString("selected")
            viewModel.loadQuestions(sportName.toString(), level = "2")

            // Показываем первый вопрос второго уровня
            currentQuestionIndex = 0
            val questionsForSport = viewModel.quizSport.value.questions
            if (currentQuestionIndex in questionsForSport.indices) {
                currentQuestion = questionsForSport[currentQuestionIndex]
                binding.tvQuestions1.text = currentQuestion.question
                binding.tvAnswer1.text = currentQuestion.answers[0]
                binding.tvAnswer2.text = currentQuestion.answers[1]
                binding.tvAnswer3.text = currentQuestion.answers[2]
            }

            // Сбрасываем allAnswersCorrect перед показом нового вопроса
            allAnswersCorrect = true

            // Разблокируем кнопки перед показом нового вопроса
            binding.cardAnswer1.isEnabled = true
            binding.cardAnswer2.isEnabled = true
            binding.cardAnswer3.isEnabled = true

            // Сбрасываем флаг ответа
            answered = false

            // Устанавливаем флаг, что уже выполнен переход на второй уровень
            isLevel2Opened = true
        } else {
            // Если есть хотя бы один неправильный ответ, блокируем кнопки ответов
            binding.cardAnswer1.isEnabled = false
            binding.cardAnswer2.isEnabled = false
            binding.cardAnswer3.isEnabled = false
            // Показываем сообщение о том, что вопросы закончились
            Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }

    // Метод для перехода к следующему вопросу
    private fun moveToNextQuestion() {
        // Разблокируем кнопки перед показом следующего вопроса
        binding.cardAnswer1.isEnabled = true
        binding.cardAnswer2.isEnabled = true
        binding.cardAnswer3.isEnabled = true

        currentQuestionIndex++
        val questionsForSport = viewModel.quizSport.value.questions

        if (currentQuestionIndex < questionsForSport.size) {
            // Показываем следующий вопрос
            currentQuestion = questionsForSport[currentQuestionIndex]
            binding.tvQuestions1.text = currentQuestion.question
            binding.tvAnswer1.text = currentQuestion.answers[0]
            binding.tvAnswer2.text = currentQuestion.answers[1]
            binding.tvAnswer3.text = currentQuestion.answers[2]
            // Сбрасываем allAnswersCorrect перед показом нового вопроса
            allAnswersCorrect = true

            // Сбрасываем флаг ответа
            answered = false
        } else {
            // Вопросы закончились, здесь обработайте завершение викторины
            // Например, покажите результаты пользователю или выполните другие действия по окончании викторины

            // Блокируем кнопки ответов
            binding.cardAnswer1.isEnabled = false
            binding.cardAnswer2.isEnabled = false
            binding.cardAnswer3.isEnabled = false

            // Показываем сообщение о том, что вопросы закончились
            Toast.makeText(requireContext(), "Вопросы закончились", Toast.LENGTH_SHORT).show()
        }
    }

    // ... (остальные методы и обработчики)

}