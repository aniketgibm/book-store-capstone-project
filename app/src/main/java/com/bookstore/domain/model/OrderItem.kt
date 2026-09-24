package com.bookstore.domain.model

data class OrderItem(
    val id: Long = 0,
    val orderId: Long,
    val book: Book,
    val quantity: Int,
    val unitPrice: Double
)
