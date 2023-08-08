package com.example.dom.usecase

import com.example.dom.model.SportQuestionModel
import com.example.dom.repository.SportsQuizRepository


class GetQuestionsUseCase(private val sportsQuizRepository: SportsQuizRepository) {

    fun getQuestions(category: String, level: Int): List<SportQuestionModel> {
        return sportsQuizRepository.getQuestion(category, level)
    }
}