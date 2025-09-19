package com.example.hopesa.data.model



data class Donation(
    val _id: String = "",
    val name: String = "",
    val amount: Double = 0.0,
    val paymentMethod: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    var status: String = "Pending", // "Pending", "Completed", "Failed"
    val transactionId: String = "", // For PayPal or bank reference
    val notes: String = ""
)

