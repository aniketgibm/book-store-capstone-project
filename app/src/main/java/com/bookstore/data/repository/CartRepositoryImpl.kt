package com.bookstore.data.repository

import com.bookstore.data.local.dao.BookDao
import com.bookstore.data.local.dao.CartDao
import com.bookstore.data.local.entity.CartItemEntity
import com.bookstore.data.mapper.toDomain
import com.bookstore.domain.model.CartItem
import com.bookstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val bookDao: BookDao
) : CartRepository {

    override fun getCartItems(userId: Long): Flow<List<CartItem>> =
        cartDao.getCartItems(userId).map { items ->
            items.mapNotNull { cartItem ->
                val book = bookDao.getBookById(cartItem.bookId) ?: return@mapNotNull null
                CartItem(
                    id = cartItem.id,
                    userId = cartItem.userId,
                    bookId = cartItem.bookId,
                    quantity = cartItem.quantity,
                    book = book.toDomain()
                )
            }
        }

    override suspend fun addToCart(userId: Long, bookId: Long, quantity: Int) {
        val exists = cartDao.existsInCart(userId, bookId)
        if (exists > 0) {
            cartDao.incrementQuantity(userId, bookId, quantity)
        } else {
            cartDao.insertCartItem(CartItemEntity(userId = userId, bookId = bookId, quantity = quantity))
        }
    }

    override suspend fun removeFromCart(cartItemId: Long) = cartDao.deleteCartItem(cartItemId)

    override suspend fun updateQuantity(cartItemId: Long, quantity: Int) =
        cartDao.updateQuantity(cartItemId, quantity)

    override suspend fun clearCart(userId: Long) = cartDao.clearCart(userId)

    override suspend fun getCartItemCount(userId: Long): Int = cartDao.getCartItemCount(userId)
}
