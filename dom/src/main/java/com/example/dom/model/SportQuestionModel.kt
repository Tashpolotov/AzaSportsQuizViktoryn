package com.example.dom.model

data class SportQuestionModel(
    val category: String,
    val level: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswer: Int
)