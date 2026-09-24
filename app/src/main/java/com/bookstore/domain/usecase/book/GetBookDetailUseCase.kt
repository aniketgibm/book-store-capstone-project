package com.bookstore.domain.usecase.book

import com.bookstore.domain.model.Book
import com.bookstore.domain.repository.BookRepository
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(bookId: Long): Book? = bookRepository.getBookById(bookId)
}
