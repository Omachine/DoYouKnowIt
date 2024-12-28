package com.example.doyouknowit.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val NeonGreen = Color(0xFF00FF00)
private val NeonCyan = Color(0xFF00FFFF)
private val NeonPink = Color(0xFFFF00FF)

private val GradientColors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))

@Composable
fun NeonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = NeonGreen,
            secondary = NeonCyan,
            background = Color.Black,
            surface = Color.Black,
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = NeonGreen,
            onSurface = NeonGreen
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = GradientColors))
        ) {
            content()
        }
    }
}