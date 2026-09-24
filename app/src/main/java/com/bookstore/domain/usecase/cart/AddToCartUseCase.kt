package com.bookstore.domain.usecase.cart

import com.bookstore.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: Long, bookId: Long, quantity: Int = 1) {
        require(quantity > 0) { "Quantity must be greater than 0" }
        cartRepository.addToCart(userId, bookId, quantity)
    }
}
