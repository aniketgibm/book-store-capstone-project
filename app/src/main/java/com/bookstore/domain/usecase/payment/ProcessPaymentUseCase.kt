package com.bookstore.domain.usecase.payment

import com.bookstore.domain.model.PaymentResult
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProcessPaymentUseCase @Inject constructor() {
    suspend operator fun invoke(
        amount: Double,
        cardNumber: String,
        cardExpiry: String,
        cardCvv: String
    ): PaymentResult {
        // Mock payment simulation — always succeeds after delay
        delay(1500)
        return PaymentResult(
            success = true,
            transactionId = "TXN${System.currentTimeMillis()}",
            message = "Payment successful"
        )
    }
}
