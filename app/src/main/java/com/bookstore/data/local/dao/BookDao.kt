package com.bookstore.data.local.dao

import androidx.room.*
import com.bookstore.data.local.entity.BookEntity
import com.bookstore.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM books WHERE categoryId = :categoryId ORDER BY title ASC")
    fun getBooksByCategory(categoryId: Long): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    suspend fun getBookById(bookId: Long): BookEntity?

    @Query("SELECT * FROM books WHERE categoryId = :categoryId AND id != :excludeBookId ORDER BY rating DESC LIMIT 5")
    fun getRelatedBooks(categoryId: Long, excludeBookId: Long): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE isFeatured = 1 ORDER BY rating DESC")
    fun getFeaturedBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchBooks(query: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBooksCount(): Int
}
