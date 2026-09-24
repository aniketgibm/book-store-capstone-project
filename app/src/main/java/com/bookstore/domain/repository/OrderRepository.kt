package com.bookstore.domain.repository

import com.bookstore.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(userId: Long): Flow<List<Order>>
    suspend fun placeOrder(order: Order): Long
    suspend fun cancelOrder(orderId: Long)
    suspend fun getOrderById(orderId: Long): Order?
    suspend fun updateOrderStatus(orderId: Long, status: String)
}
