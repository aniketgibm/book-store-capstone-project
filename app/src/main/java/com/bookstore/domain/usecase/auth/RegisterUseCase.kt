package com.bookstore.domain.usecase.auth

import com.bookstore.domain.model.User
import com.bookstore.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String, confirmPassword: String): Result<User> {
        if (name.isBlank()) return Result.failure(IllegalArgumentException("Name cannot be empty"))
        if (email.isBlank()) return Result.failure(IllegalArgumentException("Email cannot be empty"))
        if (!email.contains("@")) return Result.failure(IllegalArgumentException("Invalid email format"))
        if (password.length < 6) return Result.failure(IllegalArgumentException("Password must be at least 6 characters"))
        if (password != confirmPassword) return Result.failure(IllegalArgumentException("Passwords do not match"))
        return authRepository.register(name.trim(), email.trim(), password)
    }
}
