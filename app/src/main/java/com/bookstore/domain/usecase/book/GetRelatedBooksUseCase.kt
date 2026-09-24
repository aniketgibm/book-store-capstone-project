package com.bookstore.domain.usecase.book

import com.bookstore.domain.model.Book
import com.bookstore.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelatedBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(bookId: Long, categoryId: Long): Flow<List<Book>> =
        bookRepository.getRelatedBooks(bookId, categoryId)
}
