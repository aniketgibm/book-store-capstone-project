package com.bookstore.data.repository

import com.bookstore.data.local.dao.BookDao
import com.bookstore.data.mapper.toDomain
import com.bookstore.domain.model.Book
import com.bookstore.domain.model.Category
import com.bookstore.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
) : BookRepository {

    override fun getCategories(): Flow<List<Category>> =
        bookDao.getAllCategories().map { list -> list.map { it.toDomain() } }

    override fun getBooksByCategory(categoryId: Long): Flow<List<Book>> =
        bookDao.getBooksByCategory(categoryId).map { list -> list.map { it.toDomain() } }

    override suspend fun getBookById(bookId: Long): Book? =
        bookDao.getBookById(bookId)?.toDomain()

    override fun getRelatedBooks(bookId: Long, categoryId: Long): Flow<List<Book>> =
        bookDao.getRelatedBooks(categoryId, bookId).map { list -> list.map { it.toDomain() } }

    override fun getFeaturedBooks(): Flow<List<Book>> =
        bookDao.getFeaturedBooks().map { list -> list.map { it.toDomain() } }

    override fun searchBooks(query: String): Flow<List<Book>> =
        bookDao.searchBooks(query).map { list -> list.map { it.toDomain() } }

    override fun getAllBooks(): Flow<List<Book>> =
        bookDao.getAllBooks().map { list -> list.map { it.toDomain() } }
}
