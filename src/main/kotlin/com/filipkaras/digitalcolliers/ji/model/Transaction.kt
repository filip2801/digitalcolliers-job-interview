package com.filipkaras.digitalcolliers.ji.model

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(

    @Id
    val id: Long,
    val amount: BigDecimal,
    val customerId: Long,
    val date: LocalDateTime
)
