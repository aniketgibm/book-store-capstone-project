package com.bookstore.domain.usecase.order

import com.bookstore.domain.model.OrderStatus
import com.bookstore.domain.repository.OrderRepository
import javax.inject.Inject

private const val CANCEL_WINDOW_MS = 48 * 60 * 60 * 1000L

class CancelOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(orderId: Long): Result<Unit> {
        val order = orderRepository.getOrderById(orderId)
            ?: return Result.failure(IllegalArgumentException("Order not found"))

        if (order.status == OrderStatus.CANCELLED) {
            return Result.failure(IllegalStateException("Order is already cancelled"))
        }

        val elapsed = System.currentTimeMillis() - order.placedAt
        if (elapsed > CANCEL_WINDOW_MS) {
            return Result.failure(IllegalStateException("Order cannot be cancelled after 48 hours"))
        }

        orderRepository.cancelOrder(orderId)
        return Result.success(Unit)
    }
}
