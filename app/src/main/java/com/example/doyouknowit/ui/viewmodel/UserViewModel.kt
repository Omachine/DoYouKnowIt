// UserViewModel.kt
package com.example.doyouknowit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getUser(email: String, password: String, onResult: (FirebaseUser?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onResult(user)
                } else {
                    onResult(null)
                }
            }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun fetchHighScores(onResult: (Map<String, Int>) -> Unit) {
        val user = auth.currentUser
        user?.let {
            firestore.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val scores = document.get("highscores") as? Map<String, Int> ?: emptyMap()
                        onResult(scores)
                    } else {
                        onResult(emptyMap())
                    }
                }
                .addOnFailureListener {
                    onResult(emptyMap())
                }
        } ?: onResult(emptyMap())
    }

    fun updateHighScore(category: String, score: Int, onResult: (Boolean) -> Unit) {
        val user = auth.currentUser
        user?.let {
            val userRef = firestore.collection("users").document(it.uid)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentScores = snapshot.get("highscores") as? Map<String, Int> ?: emptyMap()
                val updatedScores = currentScores.toMutableMap()
                updatedScores[category] = score
                transaction.update(userRef, "highscores", updatedScores)
            }.addOnSuccessListener {
                onResult(true)
            }.addOnFailureListener {
                onResult(false)
            }
        } ?: onResult(false)
    }

    fun registerUser(email: String, password: String, onResult: (FirebaseUser?, Exception?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userDoc = firestore.collection("users").document(it.uid)
                        val userData = hashMapOf(
                            "email" to email,
                            "highscores" to mapOf(
                                "Computer Science" to 0,
                                "General History" to 0,
                                "Portuguese History" to 0,
                                "Video Games" to 0
                            )
                        )
                        userDoc.set(userData)
                            .addOnSuccessListener {
                                onResult(user, null)
                            }
                            .addOnFailureListener { exception ->
                                onResult(null, exception)
                            }
                    }
                } else {
                    onResult(null, task.exception)
                }
            }
    }
}