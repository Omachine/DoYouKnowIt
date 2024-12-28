// QuizViewModelFactory.kt
package com.example.doyouknowit.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizViewModelFactory(
    private val application: Application,
    private val category: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(application, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}