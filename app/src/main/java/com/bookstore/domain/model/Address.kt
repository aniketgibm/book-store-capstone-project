package com.bookstore.domain.model

data class Address(
    val id: Long = 0,
    val userId: Long,
    val label: String,
    val fullAddress: String,
    val city: String,
    val pincode: String,
    val isDefault: Boolean = false
)
