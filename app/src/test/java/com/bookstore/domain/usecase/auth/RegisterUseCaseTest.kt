package com.bookstore.domain.usecase.auth

import com.bookstore.domain.model.User
import com.bookstore.domain.repository.AuthRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var registerUseCase: RegisterUseCase

    private val testUser = User(id = 1L, name = "Alice", email = "alice@test.com", passwordHash = "hash")

    @Before
    fun setup() {
        authRepository = mockk()
        registerUseCase = RegisterUseCase(authRepository)
    }

    @Test
    fun `register with valid data succeeds`() = runTest {
        coEvery { authRepository.register("Alice", "alice@test.com", "password123") } returns Result.success(testUser)

        val result = registerUseCase("Alice", "alice@test.com", "password123", "password123")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `register with empty name fails`() = runTest {
        val result = registerUseCase("", "alice@test.com", "password123", "password123")
        assertTrue(result.isFailure)
        coVerify(exactly = 0) { authRepository.register(any(), any(), any()) }
    }

    @Test
    fun `register with mismatched passwords fails`() = runTest {
        val result = registerUseCase("Alice", "alice@test.com", "password123", "different")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("match") == true)
    }

    @Test
    fun `register with short password fails`() = runTest {
        val result = registerUseCase("Alice", "alice@test.com", "abc", "abc")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("6") == true)
    }

    @Test
    fun `register with invalid email fails`() = runTest {
        val result = registerUseCase("Alice", "invalid-email", "password123", "password123")
        assertTrue(result.isFailure)
    }
}
