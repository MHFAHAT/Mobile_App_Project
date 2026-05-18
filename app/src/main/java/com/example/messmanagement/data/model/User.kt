package com.example.messmanagement.data.model

data class User(

    val uid: String,

    val name: String,

    val role: String,

    val phone: String,

    val password: String,

    val roomNumber: String?
)