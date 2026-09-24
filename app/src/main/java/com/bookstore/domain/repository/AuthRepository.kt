package com.bookstore.domain.repository

import com.bookstore.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, password: String): Result<User>
    fun getCurrentUser(): Flow<User?>
    suspend fun logout()
    suspend fun getCurrentUserId(): Long?
}
