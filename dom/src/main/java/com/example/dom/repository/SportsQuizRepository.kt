package com.example.dom.repository

import android.content.Context
import com.example.dom.model.LevelModel
import com.example.dom.model.SportQuestionModel
import com.example.dom.model.SportsModel


interface SportsQuizRepository {

    fun getSport(name: String): List<SportsModel>

    fun getLevel(name:String) : List<LevelModel>

    fun getQuestion(name:String, level: Int) : List<SportQuestionModel>

    fun getUserProgress(category: String, context: Context): Int

    fun saveUserProgress(category: String, progress: Int, context: Context)
}