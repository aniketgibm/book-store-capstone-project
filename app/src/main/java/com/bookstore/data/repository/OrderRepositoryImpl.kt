package com.bookstore.data.repository

import com.bookstore.data.local.dao.BookDao
import com.bookstore.data.local.dao.OrderDao
import com.bookstore.data.local.entity.OrderItemEntity
import com.bookstore.data.mapper.toDomain
import com.bookstore.data.mapper.toEntity
import com.bookstore.domain.model.Order
import com.bookstore.domain.model.OrderItem
import com.bookstore.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val bookDao: BookDao
) : OrderRepository {

    override fun getOrders(userId: Long): Flow<List<Order>> =
        orderDao.getOrdersForUser(userId).map { orders ->
            orders.map { orderEntity ->
                val itemEntities = orderDao.getOrderItems(orderEntity.id)
                val items = itemEntities.mapNotNull { itemEntity ->
                    val book = bookDao.getBookById(itemEntity.bookId) ?: return@mapNotNull null
                    OrderItem(
                        id = itemEntity.id,
                        orderId = itemEntity.orderId,
                        book = book.toDomain(),
                        quantity = itemEntity.quantity,
                        unitPrice = itemEntity.unitPrice
                    )
                }
                orderEntity.toDomain(items)
            }
        }

    override suspend fun placeOrder(order: Order): Long {
        val orderId = orderDao.insertOrder(order.toEntity())
        val itemEntities = order.items.map { item ->
            OrderItemEntity(
                orderId = orderId,
                bookId = item.book.id,
                quantity = item.quantity,
                unitPrice = item.unitPrice
            )
        }
        orderDao.insertOrderItems(itemEntities)
        return orderId
    }

    override suspend fun cancelOrder(orderId: Long) {
        orderDao.updateOrderStatus(orderId, "CANCELLED")
    }

    override suspend fun getOrderById(orderId: Long): Order? {
        val orderEntity = orderDao.getOrderById(orderId) ?: return null
        val itemEntities = orderDao.getOrderItems(orderId)
        val items = itemEntities.mapNotNull { itemEntity ->
            val book = bookDao.getBookById(itemEntity.bookId) ?: return@mapNotNull null
            OrderItem(
                id = itemEntity.id,
                orderId = itemEntity.orderId,
                book = book.toDomain(),
                quantity = itemEntity.quantity,
                unitPrice = itemEntity.unitPrice
            )
        }
        return orderEntity.toDomain(items)
    }

    override suspend fun updateOrderStatus(orderId: Long, status: String) {
        orderDao.updateOrderStatus(orderId, status)
    }
}
