package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class FeeCalculator(
    private val feeWageRepository: FeeWageRepository
) {

    private val ONE_HUNDREDTH = BigDecimal.valueOf(1, 2)

    fun calculateFee(amount: BigDecimal): BigDecimal {
        val feeWage = feeWageRepository.findAllOrderedByAmountLessThan()
            .find { amount < it.transactionValueLessThan }

        return feeWage?.feePercentage
            ?.multiply(amount)
            ?.multiply(ONE_HUNDREDTH)
            ?.setScale(2, RoundingMode.DOWN)
            ?: BigDecimal.ZERO
    }
}