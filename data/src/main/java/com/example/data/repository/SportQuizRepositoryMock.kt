package com.example.data.repository

import com.example.data.R
import com.example.domain.model.LevelModel
import com.example.domain.model.SportQuestionModel
import com.example.domain.model.SportsModel
import com.example.domain.repository.SportsQuizRepository

class SportQuizRepositoryMock:SportsQuizRepository {


    override fun getSport(name:String): List<SportsModel> {
        return listOf(
            SportsModel("Football", R.drawable.football),
            SportsModel("Basketball", R.drawable.basketball),
            SportsModel("Tennis", R.drawable.tennis),
            SportsModel("Hockey", R.drawable.hockey),
        )
    }

    override fun getLevel(name: String): List<LevelModel> = when(name){
        "Football" -> listOf(LevelModel(1, "Football"),
            LevelModel(2, "Football"),
            LevelModel(3, "Football"),
            )
        "Basketball" -> listOf(LevelModel(1,"Basketball"),
            LevelModel(2,"Basketball"),
            LevelModel(3,"Basketball"),
        )
        "Tennis" -> listOf(LevelModel(
            1,"Football"),
            LevelModel(2,"Tennis"),
            LevelModel(3,"Tennis"),
        )
        "Hockey" -> listOf(LevelModel(1,"Hockey"),
            LevelModel(2,"Hockey"),
            LevelModel(3,"Hockey"),

        )
        else-> emptyList()
    }

    override fun getQuestion(name: String, level:String): List<SportQuestionModel> = when(name){
        "Football", "1" ->
            listOf(SportQuestionModel("Football", "1", "Kto ty?",
                listOf("aza", "beka", "aaa"), 0),
                (SportQuestionModel("Football", "1", "Kto ty?",
                    listOf("diana", "me", "hhh"), 2)),
                (SportQuestionModel("Football", "1", "Kto ty?",
                    listOf("ata", "me", "hhh"), 2)),
                (SportQuestionModel("Football", "1", "Kto ty?",
                    listOf("apa", "me", "hhh"), 2)),
                )


        "Football", "2" ->
            listOf(SportQuestionModel("Football", "2", "Kto ty?",
                listOf("aiba", "beka", "aaa"), 0),
                (SportQuestionModel("Football", "2", "Kto ty?",
                    listOf("dannka", "me", "hhh"), 0)),
                (SportQuestionModel("Football", "2", "Kto ty?",
                    listOf("danankiiiw", "me", "hhh"), 2)),
                (SportQuestionModel("Football", "2", "Kto ty?",
                    listOf("ttaaarynbaaaaaaa", "me", "hhh"), 2)),
            )

        "Basketball" ->
            listOf(SportQuestionModel("Basketball", "1", "Kto ty?",
                listOf("kkk", "ooo", "aaa"), 1),
                SportQuestionModel("Basketball", "1", "Kto ty?",
                    listOf("kkk", "ooo", "aaa"), 1),
                SportQuestionModel("Basketball", "1", "Kto ty?",
                    listOf("kkk", "ooo", "aaa"), 1))

        "Tennis" ->
            listOf(SportQuestionModel("Tennis", "1", "Kto ty?",
                listOf("mmm", "bevvvka", "aaa"), 0),
                SportQuestionModel("Tennis", "1", "Kto ty?",
                    listOf("mmm", "bevvvka", "aaa"), 1),
                SportQuestionModel("Tennis", "1", "Kto ty?",
                    listOf("mmm", "bevvvka", "aaa"), 1))


        "Hockey" ->
            listOf(SportQuestionModel("Hockey", "1", "Kto ty?",
                listOf("appppza", "llllll", "aaa"), 0),
                SportQuestionModel("Hockey", "1", "Kto ty?",
                    listOf("appppza", "llllll", "aaa"), 1),
                SportQuestionModel("Hockey", "1", "Kto ty?",
                    listOf("appppza", "llllll", "aaa"), 1))

        else -> emptyList()
    }
}
