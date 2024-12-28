// MainActivity.kt
package com.example.doyouknowit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.doyouknowit.ui.theme.NeonTheme
import com.example.doyouknowit.ui.AppNavigation
import com.example.doyouknowit.ui.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    fun setupFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        val persistentCacheSettings = PersistentCacheSettings.newBuilder()
            .setSizeBytes(10485760) // Set custom cache size (e.g., 10 MB)
            .build()
        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(persistentCacheSettings)
            .build()
        firestore.firestoreSettings = settings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setupFirestore()
            setContent {
                NeonTheme {
                    AppNavigation(userViewModel, startDestination = "login")
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error during onCreate", e)
        }
    }
}