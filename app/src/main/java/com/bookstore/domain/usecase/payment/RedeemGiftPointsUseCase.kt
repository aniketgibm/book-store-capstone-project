package com.bookstore.domain.usecase.payment

import javax.inject.Inject

private const val POINTS_TO_RUPEES = 0.10
private const val MIN_REDEEM_POINTS = 100

data class GiftPointsRedemption(
    val pointsToRedeem: Int,
    val discountAmount: Double,
    val remainingPoints: Int
)

class RedeemGiftPointsUseCase @Inject constructor() {
    operator fun invoke(
        availablePoints: Int,
        orderTotal: Double,
        requestedPoints: Int
    ): Result<GiftPointsRedemption> {
        if (requestedPoints < MIN_REDEEM_POINTS) {
            return Result.failure(IllegalArgumentException("Minimum $MIN_REDEEM_POINTS points required to redeem"))
        }
        if (requestedPoints > availablePoints) {
            return Result.failure(IllegalArgumentException("Not enough gift points"))
        }

        val maxDiscount = orderTotal * 0.5 // Max 50% of order can be discounted
        val requestedDiscount = requestedPoints * POINTS_TO_RUPEES
        val actualDiscount = minOf(requestedDiscount, maxDiscount)
        val actualPointsRedeemed = (actualDiscount / POINTS_TO_RUPEES).toInt()

        return Result.success(
            GiftPointsRedemption(
                pointsToRedeem = actualPointsRedeemed,
                discountAmount = actualDiscount,
                remainingPoints = availablePoints - actualPointsRedeemed
            )
        )
    }
}
