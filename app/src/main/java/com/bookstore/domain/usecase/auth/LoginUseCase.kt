package com.bookstore.domain.usecase.auth

import com.bookstore.domain.model.User
import com.bookstore.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) return Result.failure(IllegalArgumentException("Email cannot be empty"))
        if (password.isBlank()) return Result.failure(IllegalArgumentException("Password cannot be empty"))
        if (!email.contains("@")) return Result.failure(IllegalArgumentException("Invalid email format"))
        return authRepository.login(email.trim(), password)
    }
}
