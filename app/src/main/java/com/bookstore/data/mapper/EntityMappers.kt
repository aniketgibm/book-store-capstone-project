package com.bookstore.data.mapper

import com.bookstore.data.local.entity.*
import com.bookstore.domain.model.*

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    passwordHash = passwordHash,
    giftPoints = giftPoints
)

fun CategoryEntity.toDomain() = Category(id = id, name = name, iconEmoji = iconEmoji)

fun BookEntity.toDomain(categoryName: String = "") = Book(
    id = id,
    title = title,
    author = author,
    description = description,
    price = price,
    originalPrice = originalPrice,
    coverImageUrl = coverImageUrl,
    categoryId = categoryId,
    categoryName = categoryName,
    brand = brand,
    rating = rating,
    ratingCount = ratingCount,
    deliveryDays = deliveryDays,
    stockCount = stockCount,
    isFeatured = isFeatured,
    isbn = isbn
)

fun AddressEntity.toDomain() = Address(
    id = id,
    userId = userId,
    label = label,
    fullAddress = fullAddress,
    city = city,
    pincode = pincode,
    isDefault = isDefault
)

fun Address.toEntity() = AddressEntity(
    id = id,
    userId = userId,
    label = label,
    fullAddress = fullAddress,
    city = city,
    pincode = pincode,
    isDefault = isDefault
)

fun OrderEntity.toDomain(items: List<OrderItem> = emptyList()) = Order(
    id = id,
    userId = userId,
    items = items,
    totalAmount = totalAmount,
    discountAmount = discountAmount,
    deliveryFee = deliveryFee,
    status = try { OrderStatus.valueOf(status) } catch (e: Exception) { OrderStatus.CONFIRMED },
    placedAt = placedAt,
    deliveryAddress = deliveryAddress
)

fun Order.toEntity() = OrderEntity(
    id = id,
    userId = userId,
    totalAmount = totalAmount,
    discountAmount = discountAmount,
    deliveryFee = deliveryFee,
    status = status.name,
    placedAt = placedAt,
    deliveryAddress = deliveryAddress
)
