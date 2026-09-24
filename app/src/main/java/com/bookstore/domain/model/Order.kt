package com.bookstore.domain.model

enum class OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}

data class Order(
    val id: Long = 0,
    val userId: Long,
    val items: List<OrderItem> = emptyList(),
    val totalAmount: Double,
    val discountAmount: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val status: OrderStatus = OrderStatus.CONFIRMED,
    val placedAt: Long = System.currentTimeMillis(),
    val deliveryAddress: String = "",
    val canCancel: Boolean = false
)
