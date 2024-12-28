package com.example.doyouknowit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.doyouknowit.ui.theme.NeonTheme
import com.example.doyouknowit.ui.viewmodel.UserViewModel

@Composable
fun CategorySelectorScreen(navController: NavHostController, userViewModel: UserViewModel, onCategorySelected: (String, Map<String, Int>) -> Unit) {
    NeonTheme {
        val categories = listOf("Computer Science", "General History", "Portuguese History", "Video Games")
        val userEmail = userViewModel.getCurrentUser()?.email ?: "Unknown User"
        var highScores by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

        LaunchedEffect(Unit) {
            userViewModel.fetchHighScores { scores ->
                highScores = scores
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = userEmail,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                highScores.forEach { (category, score) ->
                    Box(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.25f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "$category: $score",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                categories.forEach { category ->
                    Button(
                        onClick = { onCategorySelected(category, highScores) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(category)
                    }
                }
            }
        }
    }
}