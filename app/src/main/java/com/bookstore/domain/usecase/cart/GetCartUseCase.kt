package com.bookstore.domain.usecase.cart

import com.bookstore.domain.model.CartItem
import com.bookstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(userId: Long): Flow<List<CartItem>> = cartRepository.getCartItems(userId)
}
