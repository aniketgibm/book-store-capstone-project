package com.bookstore.domain.repository

import com.bookstore.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAddresses(userId: Long): Flow<List<Address>>
    suspend fun addAddress(address: Address)
    suspend fun getUserGiftPoints(userId: Long): Int
    suspend fun updateGiftPoints(userId: Long, points: Int)
    suspend fun getUserById(userId: Long): com.bookstore.domain.model.User?
}
