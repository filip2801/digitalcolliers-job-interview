package com.filipkaras.digitalcolliers.ji.infrastructure.log

import com.filipkaras.digitalcolliers.ji.model.CustomerTransactionsSummaryDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoggingService(
    private val transactionFeeRequestLogRepository: TransactionFeeRequestLogRepository
) {

    fun log(customerTransactionsSummariesDetails: List<CustomerTransactionsSummaryDetails>) {
        val username = getUserName()
        val requestDate = LocalDateTime.now()

        val logs = customerTransactionsSummariesDetails.map {
            TransactionFeeRequestLog(
                username = username,
                customerId = it.customerId,
                fee = it.fee,
                requestDate = requestDate
            )
        }

        transactionFeeRequestLogRepository.saveAll(logs)
    }

    private fun getUserName() = SecurityContextHolder.getContext()!!.authentication!!.name

}