package com.example.dom.usecase

import android.content.Context
import com.example.dom.repository.SportsQuizRepository



class SaveUserProgressUseCase(private val sportsQuizRepository: SportsQuizRepository) {

    fun saveUserProgress(category: String, progress: Int, context: Context) {
        sportsQuizRepository.saveUserProgress(category, progress, context)
    }
}