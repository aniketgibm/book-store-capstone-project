package com.bookstore.data.local.dao

import androidx.room.*
import com.bookstore.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getCartItems(userId: Long): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItem(item: CartItemEntity): Long

    @Query("UPDATE cart_items SET quantity = quantity + :quantity WHERE userId = :userId AND bookId = :bookId")
    suspend fun incrementQuantity(userId: Long, bookId: Long, quantity: Int)

    @Query("SELECT COUNT(*) FROM cart_items WHERE userId = :userId AND bookId = :bookId")
    suspend fun existsInCart(userId: Long, bookId: Long): Int

    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItem(cartItemId: Long)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :cartItemId")
    suspend fun updateQuantity(cartItemId: Long, quantity: Int)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: Long)

    @Query("SELECT COUNT(*) FROM cart_items WHERE userId = :userId")
    suspend fun getCartItemCount(userId: Long): Int
}
