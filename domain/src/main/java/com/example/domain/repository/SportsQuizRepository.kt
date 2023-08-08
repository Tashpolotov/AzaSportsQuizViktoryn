package com.example.domain.repository

import com.example.domain.model.LevelModel
import com.example.domain.model.SportQuestionModel
import com.example.domain.model.SportsModel

interface SportsQuizRepository {

    fun getSport(name: String): List<SportsModel>

    fun getLevel(name:String) : List<LevelModel>

    fun getQuestion(name:String, level: String) : List<SportQuestionModel>

}