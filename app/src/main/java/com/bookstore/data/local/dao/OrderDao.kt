package com.bookstore.data.local.dao

import androidx.room.*
import com.bookstore.data.local.entity.OrderEntity
import com.bookstore.data.local.entity.OrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY placedAt DESC")
    fun getOrdersForUser(userId: Long): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: String)

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: Long): OrderEntity?

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItems(orderId: Long): List<OrderItemEntity>
}
