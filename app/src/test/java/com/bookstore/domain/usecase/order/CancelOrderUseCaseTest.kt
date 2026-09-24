package com.bookstore.domain.usecase.order

import com.bookstore.domain.model.*
import com.bookstore.domain.repository.OrderRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CancelOrderUseCaseTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var cancelOrderUseCase: CancelOrderUseCase

    @Before
    fun setup() {
        orderRepository = mockk()
        cancelOrderUseCase = CancelOrderUseCase(orderRepository)
    }

    private fun makeOrder(placedAt: Long, status: OrderStatus = OrderStatus.CONFIRMED) = Order(
        id = 1L,
        userId = 1L,
        totalAmount = 500.0,
        status = status,
        placedAt = placedAt
    )

    @Test
    fun `cancel order within 48 hours succeeds`() = runTest {
        val recentOrder = makeOrder(System.currentTimeMillis() - 2 * 60 * 60 * 1000L) // 2h ago
        coEvery { orderRepository.getOrderById(1L) } returns recentOrder
        coEvery { orderRepository.cancelOrder(1L) } just Runs

        val result = cancelOrderUseCase(1L)

        assertTrue(result.isSuccess)
        coVerify { orderRepository.cancelOrder(1L) }
    }

    @Test
    fun `cancel order after 48 hours fails`() = runTest {
        val oldOrder = makeOrder(System.currentTimeMillis() - 72 * 60 * 60 * 1000L) // 72h ago
        coEvery { orderRepository.getOrderById(1L) } returns oldOrder

        val result = cancelOrderUseCase(1L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
        coVerify(exactly = 0) { orderRepository.cancelOrder(any()) }
    }

    @Test
    fun `cancel already cancelled order fails`() = runTest {
        val cancelledOrder = makeOrder(System.currentTimeMillis() - 1 * 60 * 60 * 1000L, OrderStatus.CANCELLED)
        coEvery { orderRepository.getOrderById(1L) } returns cancelledOrder

        val result = cancelOrderUseCase(1L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun `cancel non-existent order fails`() = runTest {
        coEvery { orderRepository.getOrderById(99L) } returns null

        val result = cancelOrderUseCase(99L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `cancel order at exactly 48h boundary fails`() = runTest {
        val edgeOrder = makeOrder(System.currentTimeMillis() - 48 * 60 * 60 * 1000L - 1L)
        coEvery { orderRepository.getOrderById(1L) } returns edgeOrder

        val result = cancelOrderUseCase(1L)

        assertTrue(result.isFailure)
    }
}
