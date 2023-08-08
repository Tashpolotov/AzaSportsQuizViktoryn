package com.example.data.repository

import com.example.data.R
import com.example.domain.model.LevelModel
import com.example.domain.model.SportQuestionModel
import com.example.domain.model.SportsModel
import com.example.domain.repository.SportsQuizRepository

class SportQuizRepositoryMock:SportsQuizRepository {


    override fun getSport(name: String): List<SportsModel> {
        return listOf(
            SportsModel("Football", R.drawable.football),
            SportsModel("Basketball", R.drawable.basketball),
            SportsModel("Tennis", R.drawable.tennis),
            SportsModel("Hockey", R.drawable.hockey),
        )
    }

    override fun getLevel(name: String): List<LevelModel> = when (name) {
        "Football" -> listOf(
            LevelModel(1, "Football"),
            LevelModel(2, "Football"),
            LevelModel(3, "Football"),
        )
        "Basketball" -> listOf(
            LevelModel(1, "Basketball"),
            LevelModel(2, "Basketball"),
            LevelModel(3, "Basketball"),
        )
        "Tennis" -> listOf(
            LevelModel(
                1, "Football"
            ),
            LevelModel(2, "Tennis"),
            LevelModel(3, "Tennis"),
        )
        "Hockey" -> listOf(
            LevelModel(1, "Hockey"),
            LevelModel(2, "Hockey"),
            LevelModel(3, "Hockey"),

            )
        else -> emptyList()
    }

    override fun getQuestion(name: String, level: String): List<SportQuestionModel> {
        val allQuestions = mutableListOf<SportQuestionModel>()

        // Вопросы для футбола
        if (name == "Football") {
            allQuestions.addAll(
                when (level) {
                    "1" -> listOf(
                        SportQuestionModel(
                            "Football", "1", "Kto ty?",
                            listOf("aza", "beka", "aaa"), 0
                        ),
                        SportQuestionModel(
                            "Football", "1", "Kto ty?",
                            listOf("diana", "me", "hhh"), 2
                        ),
                        SportQuestionModel(
                            "Football", "1", "Kto ty?",
                            listOf("ata", "me", "hhh"), 2
                        ),
                        SportQuestionModel(
                            "Football", "1", "Kto ty?",
                            listOf("apa", "me", "hhh"), 2
                        )
                    )
                    "2" -> listOf(
                        SportQuestionModel(
                            "Football", "2", "Kto ty?",
                            listOf("aiba", "beka", "aaa"), 0
                        ),
                        SportQuestionModel(
                            "Football", "2", "Kto ty?",
                            listOf("dannka", "me", "hhh"), 0
                        ),
                        SportQuestionModel(
                            "Football", "2", "Kto ty?",
                            listOf("danankiiiw", "me", "hhh"), 2
                        ),
                        SportQuestionModel(
                            "Football", "2", "Kto ty?",
                            listOf("ttaaarynbaaaaaaa", "me", "hhh"), 2
                        )
                    )
                    "3" -> listOf(
                        SportQuestionModel(
                            "Football", "3", "Kto ty?",
                            listOf("aiba", "beka", "aaa"), 0
                        ),
                        SportQuestionModel(
                            "Football", "3", "Kto ty?",
                            listOf("dannka", "me", "hhh"), 0
                        ),
                        SportQuestionModel(
                            "Football", "3", "Kto ty?",
                            listOf("danankiiiw", "me", "hhh"), 2
                        ),
                        SportQuestionModel(
                            "Football", "3", "Kto ty?",
                            listOf("ttaaarynbaaaaaaa", "me", "hhh"), 2
                        )
                    )
                    else -> emptyList()
                }
            )
        }

        // Вопросы для баскетбола
        if (name == "Basketball") {
            allQuestions.addAll(
                when (level) {
                    "1" -> listOf(
                        SportQuestionModel(
                            "Basketball", "1", "Kto ty?",
                            listOf("kkk", "ooo", "aaa"), 1
                        ),
                        SportQuestionModel(
                            "Basketball", "1", "Kto ty?",
                            listOf("kkk", "ooo", "aaa"), 1
                        ),
                        SportQuestionModel(
                            "Basketball", "1", "Kto ty?",
                            listOf("kkk", "ooo", "aaa"), 1
                        )
                    )
                    // Добавьте вопросы для других уровней баскетбола, как указано выше
                    else -> emptyList()
                }
            )
        }

        // Вопросы для тенниса
        if (name == "Tennis") {
            allQuestions.addAll(
                when (level) {
                    "1" -> listOf(
                        SportQuestionModel(
                            "Tennis", "1", "Kto ty?",
                            listOf("mmm", "bevvvka", "aaa"), 0
                        ),
                        SportQuestionModel(
                            "Tennis", "1", "Kto ty?",
                            listOf("mmm", "bevvvka", "aaa"), 1
                        ),
                        SportQuestionModel(
                            "Tennis", "1", "Kto ty?",
                            listOf("mmm", "bevvvka", "aaa"), 1
                        )
                    )
                    // Добавьте вопросы для других уровней тенниса, как указано выше
                    else -> emptyList()
                }
            )
        }

        // Вопросы для хоккея
        if (name == "Hockey") {
            allQuestions.addAll(
                when (level) {
                    "1" -> listOf(
                        SportQuestionModel(
                            "Hockey", "1", "Kto ty?",
                            listOf("appppza", "llllll", "aaa"), 0
                        ),
                        SportQuestionModel(
                            "Hockey", "1", "Kto ty?",
                            listOf("appppza", "llllll", "aaa"), 1
                        ),
                        SportQuestionModel(
                            "Hockey", "1", "Kto ty?",
                            listOf("appppza", "llllll", "aaa"), 1
                        )
                    )
                    // Добавьте вопросы для других уровней хоккея, как указано выше
                    else -> emptyList()
                }
            )
        }

        return allQuestions
    }
}