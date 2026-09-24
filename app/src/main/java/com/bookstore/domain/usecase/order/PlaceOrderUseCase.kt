package com.bookstore.domain.usecase.order

import com.bookstore.domain.model.Order
import com.bookstore.domain.model.OrderStatus
import com.bookstore.domain.repository.CartRepository
import com.bookstore.domain.repository.OrderRepository
import com.bookstore.domain.repository.UserRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: Long,
        cartItems: List<com.bookstore.domain.model.CartItem>,
        deliveryAddress: String,
        deliveryFee: Double,
        discountAmount: Double,
        pointsRedeemed: Int
    ): Result<Long> {
        return try {
            val subtotal = cartItems.sumOf { it.book.price * it.quantity }
            val total = subtotal + deliveryFee - discountAmount

            val orderItems = cartItems.map { cartItem ->
                com.bookstore.domain.model.OrderItem(
                    orderId = 0,
                    book = cartItem.book,
                    quantity = cartItem.quantity,
                    unitPrice = cartItem.book.price
                )
            }

            val order = Order(
                userId = userId,
                items = orderItems,
                totalAmount = total,
                discountAmount = discountAmount,
                deliveryFee = deliveryFee,
                status = OrderStatus.CONFIRMED,
                placedAt = System.currentTimeMillis(),
                deliveryAddress = deliveryAddress,
                canCancel = true
            )

            val orderId = orderRepository.placeOrder(order)
            cartRepository.clearCart(userId)

            if (pointsRedeemed > 0) {
                val currentPoints = userRepository.getUserGiftPoints(userId)
                userRepository.updateGiftPoints(userId, currentPoints - pointsRedeemed)
            }

            Result.success(orderId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
