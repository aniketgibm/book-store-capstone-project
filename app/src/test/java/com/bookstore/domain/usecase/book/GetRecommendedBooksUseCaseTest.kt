package com.bookstore.domain.usecase.book

import com.bookstore.domain.model.*
import com.bookstore.domain.repository.BookRepository
import com.bookstore.domain.repository.OrderRepository
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test

class GetRecommendedBooksUseCaseTest {

    private lateinit var bookRepository: BookRepository
    private lateinit var orderRepository: OrderRepository
    private lateinit var useCase: GetRecommendedBooksUseCase

    private val cat1 = Category(1, "Fiction")
    private val cat2 = Category(2, "Science")

    private fun book(id: Long, categoryId: Long, title: String = "Book $id") =
        Book(id = id, title = title, author = "Author", description = "", price = 100.0, categoryId = categoryId, brand = "Brand")

    private fun order(vararg books: Book) = Order(
        id = 1L, userId = 1L, totalAmount = 100.0,
        items = books.map { OrderItem(orderId = 1L, book = it, quantity = 1, unitPrice = it.price) }
    )

    @Before
    fun setup() {
        bookRepository = mockk()
        orderRepository = mockk()
        useCase = GetRecommendedBooksUseCase(bookRepository, orderRepository)
    }

    @Test
    fun `returns books from purchased categories not yet purchased`() = runTest {
        val purchasedBook = book(1L, 1L)
        val rec1 = book(2L, 1L) // same category, not purchased
        val rec2 = book(3L, 2L) // different category
        val unrelated = book(4L, 1L) // same cat, also not purchased

        every { orderRepository.getOrders(1L) } returns flowOf(listOf(order(purchasedBook)))
        every { bookRepository.getAllBooks() } returns flowOf(listOf(purchasedBook, rec1, rec2, unrelated))
        every { bookRepository.getFeaturedBooks() } returns flowOf(emptyList())

        useCase(1L).test {
            val result = awaitItem()
            assertFalse(result.contains(purchasedBook))
            assertTrue(result.contains(rec1))
            assertFalse(result.contains(rec2)) // cat 2 not in order history
            assertTrue(result.contains(unrelated))
            awaitComplete()
        }
    }

    @Test
    fun `empty order history returns featured books`() = runTest {
        val featured = listOf(book(1L, 1L), book(2L, 2L))
        every { orderRepository.getOrders(1L) } returns flowOf(emptyList())
        every { bookRepository.getFeaturedBooks() } returns flowOf(featured)
        every { bookRepository.getAllBooks() } returns flowOf(emptyList())

        useCase(1L).test {
            val result = awaitItem()
            assertEquals(featured, result)
            awaitComplete()
        }
    }

    @Test
    fun `all category books purchased returns empty`() = runTest {
        val b1 = book(1L, 1L)
        val b2 = book(2L, 1L)
        every { orderRepository.getOrders(1L) } returns flowOf(listOf(order(b1, b2)))
        every { bookRepository.getAllBooks() } returns flowOf(listOf(b1, b2))
        every { bookRepository.getFeaturedBooks() } returns flowOf(emptyList())

        useCase(1L).test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            awaitComplete()
        }
    }
}
