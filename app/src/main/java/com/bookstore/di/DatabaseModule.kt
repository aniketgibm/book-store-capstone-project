package com.bookstore.di

import android.content.Context
import androidx.room.Room
import com.bookstore.data.local.BookStoreDatabase
import com.bookstore.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookStoreDatabase {
        lateinit var db: BookStoreDatabase
        db = Room.databaseBuilder(context, BookStoreDatabase::class.java, "bookstore.db")
            .addCallback(BookStoreDatabase.SeedCallback(
                userDao = { db.userDao() },
                bookDao = { db.bookDao() },
                addressDao = { db.addressDao() },
                orderDao = { db.orderDao() }
            ))
            .fallbackToDestructiveMigration()
            .build()
        return db
    }

    @Provides
    fun provideUserDao(db: BookStoreDatabase): UserDao = db.userDao()

    @Provides
    fun provideAddressDao(db: BookStoreDatabase): AddressDao = db.addressDao()

    @Provides
    fun provideBookDao(db: BookStoreDatabase): BookDao = db.bookDao()

    @Provides
    fun provideCartDao(db: BookStoreDatabase): CartDao = db.cartDao()

    @Provides
    fun provideOrderDao(db: BookStoreDatabase): OrderDao = db.orderDao()
}
