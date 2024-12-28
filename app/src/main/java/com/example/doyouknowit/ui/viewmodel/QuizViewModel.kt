package com.example.doyouknowit.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import com.example.doyouknowit.model.Question

class QuizViewModel(application: Application, category: String) : AndroidViewModel(application) {
    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    init {
        val json = loadJsonFromAssets("${category.toLowerCase().replace(" ", "_")}_questions.json")
        val questionType = object : TypeToken<List<Question>>() {}.type
        _questions.value = Gson().fromJson<List<Question>>(json, questionType).shuffled()
    }

    private fun loadJsonFromAssets(fileName: String): String {
        val inputStream: InputStream = getApplication<Application>().assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}