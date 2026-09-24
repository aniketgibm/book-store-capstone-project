package com.bookstore.domain.model

data class User(
    val id: Long = 0,
    val name: String,
    val email: String,
    val passwordHash: String,
    val giftPoints: Int = 0,
    val addresses: List<Address> = emptyList()
)
