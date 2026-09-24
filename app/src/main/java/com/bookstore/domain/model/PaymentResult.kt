package com.bookstore.domain.model

data class PaymentResult(
    val success: Boolean,
    val transactionId: String = "",
    val message: String = ""
)
