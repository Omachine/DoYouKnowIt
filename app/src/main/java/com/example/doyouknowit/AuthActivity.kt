// AuthActivity.kt
package com.example.doyouknowit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf("") }

            Column {
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
                Button(onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@AuthActivity) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                                finish()
                            } else {
                                errorMessage = task.exception?.message ?: "Authentication failed"
                            }
                        }
                }) {
                    Text("Sign In")
                }
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}