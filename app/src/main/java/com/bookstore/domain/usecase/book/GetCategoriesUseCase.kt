package com.bookstore.domain.usecase.book

import com.bookstore.domain.model.Category
import com.bookstore.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<List<Category>> = bookRepository.getCategories()
}
