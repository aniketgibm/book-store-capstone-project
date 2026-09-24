package com.bookstore.data.local.dao

import androidx.room.*
import com.bookstore.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("UPDATE users SET giftPoints = :points WHERE id = :userId")
    suspend fun updateGiftPoints(userId: Long, points: Int)

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): UserEntity?

    @Query("SELECT giftPoints FROM users WHERE id = :userId")
    suspend fun getGiftPoints(userId: Long): Int
}
