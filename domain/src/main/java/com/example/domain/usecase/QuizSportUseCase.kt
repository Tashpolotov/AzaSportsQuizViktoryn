package com.example.domain.usecase

import com.example.domain.repository.SportsQuizRepository

class QuizSportUseCase(private val sportsQuizRepository: SportsQuizRepository) {

    operator fun invoke(completedLevel: Int) {
        sportsQuizRepository.getSport(name = "someName")
        sportsQuizRepository.getLevel(name = "someName")
        sportsQuizRepository.getQuestion(name = "someName", level = "someLevel")
        sportsQuizRepository.updateCompletedLevels(level = completedLevel)
        sportsQuizRepository.getCompletedLevels()
    }
}