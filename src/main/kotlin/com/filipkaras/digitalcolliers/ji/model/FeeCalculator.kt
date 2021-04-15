package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class FeeCalculator(
    private val feeWageRepository: FeeWageRepository
) {

    private val ONE_HUNDREDTH = BigDecimal.valueOf(0.01)

    fun calculateFee(amount: BigDecimal): BigDecimal {
        val feeWage = feeWageRepository.findAllOrderedByAmountLessThan()
            .find { amount < it.transactionValueLessThan }

        return feeWage?.feePercentage?.multiply(amount)?.multiply(ONE_HUNDREDTH) ?: BigDecimal.ZERO
    }
}