package com.bookstore.domain.usecase.auth

import com.bookstore.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.logout()
}
