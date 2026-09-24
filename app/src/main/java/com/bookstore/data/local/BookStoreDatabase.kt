package com.bookstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bookstore.data.local.dao.*
import com.bookstore.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        AddressEntity::class,
        CategoryEntity::class,
        BookEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BookStoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun addressDao(): AddressDao
    abstract fun bookDao(): BookDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    class SeedCallback(
        private val userDao: () -> UserDao,
        private val bookDao: () -> BookDao,
        private val addressDao: () -> AddressDao,
        private val orderDao: () -> OrderDao
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                DatabaseSeeder.seed(userDao(), bookDao(), addressDao(), orderDao())
            }
        }
    }
}
