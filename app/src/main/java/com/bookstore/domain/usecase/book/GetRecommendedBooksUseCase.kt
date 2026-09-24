package com.bookstore.domain.usecase.book

import com.bookstore.domain.model.Book
import com.bookstore.domain.repository.BookRepository
import com.bookstore.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecommendedBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val orderRepository: OrderRepository
) {
    operator fun invoke(userId: Long): Flow<List<Book>> = flow {
        val orders = orderRepository.getOrders(userId).first()
        val purchasedBookIds = orders.flatMap { order -> order.items.map { it.book.id } }.toSet()
        val categoryIds = orders.flatMap { order -> order.items.map { it.book.categoryId } }.toSet()

        if (categoryIds.isEmpty()) {
            // No order history — return featured books as fallback
            val featured = bookRepository.getFeaturedBooks().first()
            emit(featured.take(10))
            return@flow
        }

        val allBooks = bookRepository.getAllBooks().first()
        val recommendations = allBooks.filter { book ->
            book.categoryId in categoryIds && book.id !in purchasedBookIds
        }.take(10)

        emit(recommendations)
    }
}
