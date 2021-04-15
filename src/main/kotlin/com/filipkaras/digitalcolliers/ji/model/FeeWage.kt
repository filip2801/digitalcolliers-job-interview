package com.filipkaras.digitalcolliers.ji.model

import java.math.BigDecimal

data class FeeWage(
    val transactionValueLessThan: BigDecimal,
    val feePercentage: BigDecimal
)
