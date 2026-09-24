package com.bookstore.domain.usecase.auth

import com.bookstore.domain.model.User
import com.bookstore.domain.repository.AuthRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    private val testUser = User(id = 1L, name = "Alice", email = "alice@test.com", passwordHash = "hash")

    @Before
    fun setup() {
        authRepository = mockk()
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `login with valid credentials succeeds`() = runTest {
        coEvery { authRepository.login("alice@test.com", "password123") } returns Result.success(testUser)

        val result = loginUseCase("alice@test.com", "password123")

        assertTrue(result.isSuccess)
        assertEquals(testUser, result.getOrNull())
    }

    @Test
    fun `login with empty email fails without hitting repo`() = runTest {
        val result = loginUseCase("", "password123")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        coVerify(exactly = 0) { authRepository.login(any(), any()) }
    }

    @Test
    fun `login with invalid email format fails`() = runTest {
        val result = loginUseCase("notanemail", "password123")

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { authRepository.login(any(), any()) }
    }

    @Test
    fun `login with empty password fails without hitting repo`() = runTest {
        val result = loginUseCase("alice@test.com", "")

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { authRepository.login(any(), any()) }
    }

    @Test
    fun `login with wrong password returns repo failure`() = runTest {
        coEvery { authRepository.login("alice@test.com", "wrongpass") } returns
            Result.failure(IllegalArgumentException("Incorrect password"))

        val result = loginUseCase("alice@test.com", "wrongpass")

        assertTrue(result.isFailure)
    }
}
