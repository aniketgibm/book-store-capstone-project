package com.bookstore.domain.usecase.order

import com.bookstore.domain.repository.CartRepository
import com.bookstore.domain.repository.OrderRepository
import javax.inject.Inject

class BuyAgainUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(orderId: Long, userId: Long): Result<Unit> {
        return try {
            val order = orderRepository.getOrderById(orderId)
                ?: return Result.failure(IllegalArgumentException("Order not found"))

            order.items.forEach { item ->
                cartRepository.addToCart(userId, item.book.id, item.quantity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
