// utils/registerUser.kt
package com.example.doyouknowit.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun registerUser(email: String, password: String, additionalData: Map<String, Any>, onResult: (FirebaseUser?, Exception?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let {
                    firestore.collection("users").document(it.uid).set(additionalData)
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