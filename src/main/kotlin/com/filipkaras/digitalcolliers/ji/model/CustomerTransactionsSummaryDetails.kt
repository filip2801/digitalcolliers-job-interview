package com.filipkaras.digitalcolliers.ji.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class CustomerTransactionsSummaryDetails (
    val customerFirstName: String,
    val customerLastName: String,
    val customerId: Long,
    val totalAmount: BigDecimal,
    val fee: BigDecimal,
    val numberOfTransactions: Long,
    @get:JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    val lastTransactionDate: LocalDateTime
)
