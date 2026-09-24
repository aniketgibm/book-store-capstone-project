package com.bookstore.domain.model

data class CartItem(
    val id: Long = 0,
    val userId: Long,
    val bookId: Long,
    val quantity: Int,
    val book: Book
)
