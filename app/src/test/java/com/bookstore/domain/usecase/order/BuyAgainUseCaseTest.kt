package com.bookstore.domain.usecase.order

import com.bookstore.domain.model.*
import com.bookstore.domain.repository.CartRepository
import com.bookstore.domain.repository.OrderRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BuyAgainUseCaseTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var buyAgainUseCase: BuyAgainUseCase

    private fun book(id: Long) = Book(id = id, title = "Book $id", author = "Author", description = "", price = 100.0, categoryId = 1L, brand = "Brand")
    private fun orderItem(book: Book, qty: Int) = OrderItem(orderId = 1L, book = book, quantity = qty, unitPrice = book.price)

    @Before
    fun setup() {
        orderRepository = mockk()
        cartRepository = mockk()
        buyAgainUseCase = BuyAgainUseCase(orderRepository, cartRepository)
    }

    @Test
    fun `buy again adds all items to cart`() = runTest {
        val b1 = book(1L)
        val b2 = book(2L)
        val order = Order(id = 1L, userId = 1L, totalAmount = 200.0, items = listOf(orderItem(b1, 1), orderItem(b2, 2)))
        coEvery { orderRepository.getOrderById(1L) } returns order
        coEvery { cartRepository.addToCart(1L, b1.id, 1) } just Runs
        coEvery { cartRepository.addToCart(1L, b2.id, 2) } just Runs

        val result = buyAgainUseCase(1L, 1L)

        assertTrue(result.isSuccess)
        coVerify { cartRepository.addToCart(1L, b1.id, 1) }
        coVerify { cartRepository.addToCart(1L, b2.id, 2) }
    }

    @Test
    fun `buy again with non-existent order fails`() = runTest {
        coEvery { orderRepository.getOrderById(99L) } returns null

        val result = buyAgainUseCase(99L, 1L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}
