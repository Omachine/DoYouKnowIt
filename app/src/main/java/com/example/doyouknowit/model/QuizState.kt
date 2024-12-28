package com.example.doyouknowit.model

data class QuizState(
    val isGameOver: Boolean = false,
    val score: Int = 0,
    val currentQuestionIndex: Int = 0,
    val questions: List<Question> = emptyList()
)