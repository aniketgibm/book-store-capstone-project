package com.bookstore.domain.repository

import com.bookstore.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(userId: Long): Flow<List<CartItem>>
    suspend fun addToCart(userId: Long, bookId: Long, quantity: Int)
    suspend fun removeFromCart(cartItemId: Long)
    suspend fun updateQuantity(cartItemId: Long, quantity: Int)
    suspend fun clearCart(userId: Long)
    suspend fun getCartItemCount(userId: Long): Int
}
