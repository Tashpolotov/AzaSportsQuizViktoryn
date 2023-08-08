package com.example.dom.usecase

import com.example.dom.repository.SportsQuizRepository

class QuizSportUseCase(private val sportsQuizRepository: SportsQuizRepository) {

    operator fun invoke(){
        sportsQuizRepository.getSport(name = String())
        sportsQuizRepository.getLevel(name = String())

    }
}