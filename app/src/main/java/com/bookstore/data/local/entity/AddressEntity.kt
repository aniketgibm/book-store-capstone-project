package com.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val label: String,
    val fullAddress: String,
    val city: String,
    val pincode: String,
    val isDefault: Boolean = false
)
