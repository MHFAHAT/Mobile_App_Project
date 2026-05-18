package com.example.messmanagement.data.model

data class Payment(
    val paymentId: Int,
    val userId: String,
    val monthYear: String,
    val mealCount: Int,
    val mealRate: Double,
    val totalAmount: Double,
    val status: String,
    val paidDate: String?
)