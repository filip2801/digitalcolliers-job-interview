package com.filipkaras.digitalcolliers.ji.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
    val id: Long,
    val amount: BigDecimal,
    val customerDto: CustomerDto,
    val date: LocalDateTime
)
