package com.bookstore.domain.repository

import com.bookstore.domain.model.Book
import com.bookstore.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getCategories(): Flow<List<Category>>
    fun getBooksByCategory(categoryId: Long): Flow<List<Book>>
    suspend fun getBookById(bookId: Long): Book?
    fun getRelatedBooks(bookId: Long, categoryId: Long): Flow<List<Book>>
    fun getFeaturedBooks(): Flow<List<Book>>
    fun searchBooks(query: String): Flow<List<Book>>
    fun getAllBooks(): Flow<List<Book>>
}
