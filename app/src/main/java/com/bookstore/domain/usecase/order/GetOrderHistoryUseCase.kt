package com.bookstore.domain.usecase.order

import com.bookstore.domain.model.Order
import com.bookstore.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val CANCEL_WINDOW_MS = 48 * 60 * 60 * 1000L // 48 hours

class GetOrderHistoryUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(userId: Long): Flow<List<Order>> =
        orderRepository.getOrders(userId).map { orders ->
            orders.map { order ->
                val withinWindow = (System.currentTimeMillis() - order.placedAt) < CANCEL_WINDOW_MS
                order.copy(canCancel = withinWindow && order.status != com.bookstore.domain.model.OrderStatus.CANCELLED)
            }.sortedByDescending { it.placedAt }
        }
}
