package com.example.messmanagement.data.model

data class Expense(
    val expenseId: Int,
    val date: String,
    val category: String,
    val amount: Double,
    val enteredBy: String?
)