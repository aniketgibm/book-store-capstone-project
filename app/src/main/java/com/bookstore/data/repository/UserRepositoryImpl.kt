package com.bookstore.data.repository

import com.bookstore.data.local.dao.AddressDao
import com.bookstore.data.local.dao.UserDao
import com.bookstore.data.mapper.toDomain
import com.bookstore.data.mapper.toEntity
import com.bookstore.domain.model.Address
import com.bookstore.domain.model.User
import com.bookstore.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val addressDao: AddressDao
) : UserRepository {

    override fun getAddresses(userId: Long): Flow<List<Address>> =
        addressDao.getAddressesForUser(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun addAddress(address: Address) =
        addressDao.insertAddress(address.toEntity())

    override suspend fun getUserGiftPoints(userId: Long): Int =
        userDao.getGiftPoints(userId)

    override suspend fun updateGiftPoints(userId: Long, points: Int) =
        userDao.updateGiftPoints(userId, points)

    override suspend fun getUserById(userId: Long): User? =
        userDao.getUserById(userId)?.toDomain()
}
