package com.example.doyouknowit.model

import org.json.JSONObject

data class Question(
    val text: String,
    val choices: List<String>,
    val correctAnswer: String
) {
    companion object {
        fun fromJson(json: JSONObject): Question {
            val text = json.getString("text")
            val choices = json.getJSONArray("choices")
            val correctAnswer = json.getString("correctAnswer")

            val choicesList = mutableListOf<String>()
            for (i in 0 until choices.length()) {
                choicesList.add(choices.getString(i))
            }

            return Question(
                text = text,
                choices = choicesList,
                correctAnswer = correctAnswer
            )
        }
    }
}