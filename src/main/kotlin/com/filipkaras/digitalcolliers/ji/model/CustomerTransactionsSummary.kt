package com.filipkaras.digitalcolliers.ji.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class CustomerTransactionsSummary (
    val id: Long,
    val totalAmount: BigDecimal,
    val numberOfTransactions: Long,
    val lastTransactionDate: LocalDateTime
)