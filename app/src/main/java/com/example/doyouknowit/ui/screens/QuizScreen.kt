package com.example.doyouknowit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.doyouknowit.ui.theme.NeonTheme
import com.example.doyouknowit.ui.viewmodel.QuizViewModel
import com.example.doyouknowit.ui.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import com.example.doyouknowit.ui.viewmodel.QuizViewModelFactory
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    category: String,
    highScores: Map<String, Int>,
    onResult: (Boolean) -> Unit
) {
    NeonTheme {
        val context = LocalContext.current
        val quizViewModel: QuizViewModel = viewModel(factory = QuizViewModelFactory(context.applicationContext as Application, category))
        val questions by quizViewModel.questions.observeAsState(emptyList())
        var currentQuestionIndex by remember { mutableStateOf(0) }
        var selectedAnswer by remember { mutableStateOf<String?>(null) }
        var timer by remember { mutableStateOf(60) }
        var score by remember { mutableStateOf(0) }
        var quizCompleted by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            while (timer > 0 && !quizCompleted) {
                delay(1000L)
                timer--
            }
        }

        if (timer == 0 || currentQuestionIndex >= questions.size) {
            quizCompleted = true
        }

        if (quizCompleted) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Your score: $score", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val previousHighScore = highScores[category] ?: 0
                            if (score > previousHighScore) {
                                userViewModel.updateHighScore(category, score) { success ->
                                    if (success) {
                                        navController.navigate("category_selector") {
                                            popUpTo("category_selector") { inclusive = true }
                                        }
                                    } else {
                                        // Handle error
                                    }
                                }
                            } else {
                                navController.navigate("category_selector") {
                                    popUpTo("category_selector") { inclusive = true }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Finish", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        } else {
            val question = questions[currentQuestionIndex]
            val shuffledChoices = remember(question) { question.choices.shuffled() }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(question.text, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                shuffledChoices.forEach { choice: String ->
                    Button(
                        onClick = {
                            selectedAnswer = choice
                            coroutineScope.launch {
                                delay(1000L)
                                if (choice == question.correctAnswer) {
                                    score++
                                }
                                currentQuestionIndex++
                                selectedAnswer = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAnswer == null) MaterialTheme.colorScheme.primary else Color.Gray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            choice,
                            color = when {
                                selectedAnswer == choice && choice == question.correctAnswer -> Color.Green
                                selectedAnswer == choice && choice != question.correctAnswer -> Color.Red
                                selectedAnswer != null && choice == question.correctAnswer -> Color.Green
                                else -> MaterialTheme.colorScheme.onPrimary
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Time left: $timer seconds", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { quizCompleted = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Finish Early", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}