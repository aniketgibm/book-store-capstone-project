package com.bookstore.data.local.dao

import androidx.room.*
import com.bookstore.data.local.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses WHERE userId = :userId")
    fun getAddressesForUser(userId: Long): Flow<List<AddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Query("DELETE FROM addresses WHERE id = :addressId")
    suspend fun deleteAddress(addressId: Long)
}
