package com.filipkaras.digitalcolliers.ji.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val amount: BigDecimal,
    val customerId: Long,
    val date: LocalDateTime
)
