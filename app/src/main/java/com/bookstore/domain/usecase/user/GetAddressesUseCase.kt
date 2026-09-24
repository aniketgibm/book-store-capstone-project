package com.bookstore.domain.usecase.user

import com.bookstore.domain.model.Address
import com.bookstore.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: Long): Flow<List<Address>> = userRepository.getAddresses(userId)
}
