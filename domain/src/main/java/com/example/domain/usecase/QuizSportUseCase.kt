package com.example.domain.usecase

import com.example.domain.repository.SportsQuizRepository

class QuizSportUseCase(private val sportsQuizRepository: SportsQuizRepository) {

    operator fun invoke(){
        sportsQuizRepository.getSport(name = String())
        sportsQuizRepository.getLevel(name = String())
        sportsQuizRepository.getQuestion(name = String(), level = String())
    }
}