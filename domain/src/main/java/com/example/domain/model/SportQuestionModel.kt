package com.example.domain.model

data class SportQuestionModel(
    val category: String,
    val level: String,
    val question: String,
    val answers: List<String>,
    var correctAnswer: Int,

)