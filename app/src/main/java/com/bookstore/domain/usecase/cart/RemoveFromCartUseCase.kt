package com.bookstore.domain.usecase.cart

import com.bookstore.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: Long) = cartRepository.removeFromCart(cartItemId)
}
