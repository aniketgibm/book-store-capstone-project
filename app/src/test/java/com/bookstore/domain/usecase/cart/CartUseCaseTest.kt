package com.bookstore.domain.usecase.cart

import com.bookstore.domain.repository.CartRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var updateCartQuantityUseCase: UpdateCartQuantityUseCase

    @Before
    fun setup() {
        cartRepository = mockk()
        addToCartUseCase = AddToCartUseCase(cartRepository)
        updateCartQuantityUseCase = UpdateCartQuantityUseCase(cartRepository)
    }

    @Test
    fun `add to cart with valid quantity succeeds`() = runTest {
        coEvery { cartRepository.addToCart(1L, 5L, 1) } just Runs

        addToCartUseCase(1L, 5L, 1)

        coVerify { cartRepository.addToCart(1L, 5L, 1) }
    }

    @Test
    fun `add to cart with zero quantity throws`() = runTest {
        assertThrows(IllegalArgumentException::class.java) {
            kotlinx.coroutines.runBlocking { addToCartUseCase(1L, 5L, 0) }
        }
    }

    @Test
    fun `add to cart with negative quantity throws`() = runTest {
        assertThrows(IllegalArgumentException::class.java) {
            kotlinx.coroutines.runBlocking { addToCartUseCase(1L, 5L, -1) }
        }
    }

    @Test
    fun `update quantity with valid value succeeds`() = runTest {
        coEvery { cartRepository.updateQuantity(1L, 3) } just Runs

        updateCartQuantityUseCase(1L, 3)

        coVerify { cartRepository.updateQuantity(1L, 3) }
    }

    @Test
    fun `update quantity with zero throws`() = runTest {
        assertThrows(IllegalArgumentException::class.java) {
            kotlinx.coroutines.runBlocking { updateCartQuantityUseCase(1L, 0) }
        }
    }
}
