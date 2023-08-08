package com.example.azasportsquizviktoryn.viewmodel

import com.example.domain.model.LevelModel
import com.example.domain.model.SportQuestionModel
import com.example.domain.model.SportsModel

data class SportQuizStateFlow (
        val sports: List<SportsModel> = emptyList(),

        val level: List<LevelModel> = emptyList(),

        val questions: List<SportQuestionModel> = emptyList(),

        val lastUnlockedLevel: Int = 1
)