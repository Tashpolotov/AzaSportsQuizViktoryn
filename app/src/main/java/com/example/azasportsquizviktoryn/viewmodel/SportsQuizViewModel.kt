package com.example.azasportsquizviktoryn.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LevelModel
import com.example.domain.repository.SportsQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsQuizViewModel @Inject constructor(val repository: SportsQuizRepository) : ViewModel() {

    private val _quizSport = MutableStateFlow(SportQuizStateFlow())
    val quizSport: StateFlow<SportQuizStateFlow> = _quizSport.asStateFlow()

    // Метод для загрузки данных о спорте
    fun loadSports(name: String) {
        viewModelScope.launch {
            val sportsList = repository.getSport(name)
            _quizSport.value = _quizSport.value.copy(sports = sportsList)
        }
    }

    // Метод для загрузки данных об уровне
    fun loadSportLevel(name: String) {
        viewModelScope.launch {
            val sportLevel = repository.getLevel(name)
            _quizSport.value = _quizSport.value.copy(level = sportLevel)
        }
    }

    // Метод для загрузки вопросов
    fun loadQuestions(name: String, level:String) {
        viewModelScope.launch {
            Log.e("SportsQuizViewModel", "Loading questions for sport: $name")
            val allQuestions = repository.getQuestion(name, level)
            _quizSport.value = _quizSport.value.copy(questions = allQuestions)
            Log.e("SportsQuizViewModel", "Loaded ${allQuestions.size} questions for sport: $name")
        }
    }
}