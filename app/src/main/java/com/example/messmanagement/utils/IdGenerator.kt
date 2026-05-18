package com.example.messmanagement.utils

object IdGenerator {

    fun generateUserId(count: Int): String {

        val nextNumber = count + 1

        return "U" + nextNumber.toString()
            .padStart(3, '0')
    }
}