package com.bookstore.data.repository

import com.bookstore.data.local.DatabaseSeeder
import com.bookstore.data.local.SessionDataStore
import com.bookstore.data.local.dao.UserDao
import com.bookstore.data.mapper.toDomain
import com.bookstore.domain.model.User
import com.bookstore.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sessionDataStore: SessionDataStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        val entity = userDao.getUserByEmail(email)
            ?: return Result.failure(IllegalArgumentException("No account found with this email"))
        val hash = DatabaseSeeder.hashPassword(password)
        if (entity.passwordHash != hash) {
            return Result.failure(IllegalArgumentException("Incorrect password"))
        }
        sessionDataStore.saveSession(entity.id)
        return Result.success(entity.toDomain())
    }

    override suspend fun register(name: String, email: String, password: String): Result<User> {
        val existing = userDao.getUserByEmail(email)
        if (existing != null) {
            return Result.failure(IllegalArgumentException("An account with this email already exists"))
        }
        val entity = com.bookstore.data.local.entity.UserEntity(
            name = name,
            email = email,
            passwordHash = DatabaseSeeder.hashPassword(password),
            giftPoints = 100 // Welcome bonus
        )
        val id = userDao.insertUser(entity)
        sessionDataStore.saveSession(id)
        return Result.success(entity.copy(id = id).toDomain())
    }

    override fun getCurrentUser(): Flow<User?> = flow {
        val userId = sessionDataStore.currentUserId.first() ?: run {
            emit(null); return@flow
        }
        val entity = userDao.getUserById(userId)
        emit(entity?.toDomain())
    }

    override suspend fun logout() {
        sessionDataStore.clearSession()
    }

    override suspend fun getCurrentUserId(): Long? = sessionDataStore.currentUserId.first()
}
