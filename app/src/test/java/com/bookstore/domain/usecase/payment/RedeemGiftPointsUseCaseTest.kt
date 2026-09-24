package com.bookstore.domain.usecase.payment

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RedeemGiftPointsUseCaseTest {

    private lateinit var useCase: RedeemGiftPointsUseCase

    @Before
    fun setup() {
        useCase = RedeemGiftPointsUseCase()
    }

    @Test
    fun `redeem valid points succeeds`() {
        val result = useCase(500, 1000.0, 200)
        assertTrue(result.isSuccess)
        val redemption = result.getOrNull()!!
        assertEquals(200, redemption.pointsToRedeem)
        assertEquals(20.0, redemption.discountAmount, 0.01)
        assertEquals(300, redemption.remainingPoints)
    }

    @Test
    fun `redeem below minimum threshold fails`() {
        val result = useCase(500, 1000.0, 50)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `redeem more points than available fails`() {
        val result = useCase(100, 1000.0, 200)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `discount capped at 50 percent of order total`() {
        // 10000 points = Rs 1000 discount, but order is Rs 500 so max is Rs 250
        val result = useCase(10000, 500.0, 10000)
        assertTrue(result.isSuccess)
        val redemption = result.getOrNull()!!
        assertEquals(250.0, redemption.discountAmount, 0.01) // max 50% of 500
    }

    @Test
    fun `zero points always fails`() {
        val result = useCase(500, 1000.0, 0)
        assertTrue(result.isFailure)
    }

    @Test
    fun `exact minimum points succeeds`() {
        val result = useCase(500, 1000.0, 100)
        assertTrue(result.isSuccess)
        val redemption = result.getOrNull()!!
        assertEquals(100, redemption.pointsToRedeem)
        assertEquals(10.0, redemption.discountAmount, 0.01)
    }
}
