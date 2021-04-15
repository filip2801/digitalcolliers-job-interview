package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Component

@Component
class FeeWageRepository  {

    private var orderedFeeWages: List<FeeWage> = listOf()

    fun setFeeWages(feeWages: List<FeeWage>) {
        orderedFeeWages = feeWages.sortedBy { it.transactionValueLessThan }
    }

    fun findAllOrderedByAmountLessThan(): List<FeeWage> {
        return orderedFeeWages
    }
}