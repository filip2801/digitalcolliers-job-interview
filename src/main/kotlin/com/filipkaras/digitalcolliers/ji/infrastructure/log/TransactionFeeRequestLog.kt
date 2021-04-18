package com.filipkaras.digitalcolliers.ji.infrastructure.log

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TransactionFeeRequestLog (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val customerId: Long,
    val fee: BigDecimal,
    val requestDate: LocalDateTime
)