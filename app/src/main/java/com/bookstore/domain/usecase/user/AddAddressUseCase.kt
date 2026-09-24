package com.bookstore.domain.usecase.user

import com.bookstore.domain.model.Address
import com.bookstore.domain.repository.UserRepository
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(address: Address): Result<Unit> {
        if (address.fullAddress.isBlank()) return Result.failure(IllegalArgumentException("Address cannot be empty"))
        if (address.city.isBlank()) return Result.failure(IllegalArgumentException("City cannot be empty"))
        if (address.pincode.length != 6) return Result.failure(IllegalArgumentException("Pincode must be 6 digits"))
        userRepository.addAddress(address)
        return Result.success(Unit)
    }
}
