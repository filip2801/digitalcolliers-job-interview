package com.filipkaras.digitalcolliers.ji.web

import com.filipkaras.digitalcolliers.ji.infrastructure.log.LoggingService
import com.filipkaras.digitalcolliers.ji.model.CustomerTransactionsFeeService
import com.filipkaras.digitalcolliers.ji.model.CustomerTransactionsSummaryDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions/fee")
class TransactionSummaryController(
    private val transactionsFeeService: CustomerTransactionsFeeService,
    private val loggingService: LoggingService
) {

    @GetMapping
    fun getCustomerTransactionSummary(@RequestParam("customer_id") customerIds: List<Long>): List<CustomerTransactionsSummaryDetails> {
        val transactionsSummariesDetails = transactionsFeeService.calculateCustomerTransactionFee(customerIds)
        log(transactionsSummariesDetails)

        return transactionsSummariesDetails
    }

    @GetMapping(params = ["customer_id=ALL"])
    fun getCustomerTransactionSummaryForAll(): List<CustomerTransactionsSummaryDetails> {
        val transactionsSummariesDetails = transactionsFeeService.calculateCustomerTransactionFeeForAll()
        log(transactionsSummariesDetails)

        return transactionsSummariesDetails
    }

    private fun log(transactionsSummariesDetails: List<CustomerTransactionsSummaryDetails>) {
        loggingService.log(transactionsSummariesDetails)
    }

}