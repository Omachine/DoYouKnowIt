package com.example.doyouknowit.data.entities

data class User(
    val id: Int = 0,
    val username: String = "",
    val password: String = ""
) {
    // No-argument constructor for Firebase
    constructor() : this(0, "", "")
}