package com.bookstore.domain.usecase.cart

import com.bookstore.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: Long, quantity: Int) {
        require(quantity > 0) { "Quantity must be greater than 0" }
        cartRepository.updateQuantity(cartItemId, quantity)
    }
}
