package com.filipkaras.digitalcolliers.ji.web

import com.filipkaras.digitalcolliers.ji.model.CustomerTransactionsFeeService
import com.filipkaras.digitalcolliers.ji.model.CustomerTransactionsSummaryDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions/fee")
class TransactionSummaryController(
    private val transactionsFeeService: CustomerTransactionsFeeService
) {

    @GetMapping
    fun getCustomerTransactionSummary(@RequestParam("customer_id") customerIds: List<Long>): List<CustomerTransactionsSummaryDetails> {
        return transactionsFeeService.calculateCustomerTransactionFee(customerIds)
    }

    @GetMapping(params = ["customer_id=ALL"])
    fun getCustomerTransactionSummaryForAll(): List<CustomerTransactionsSummaryDetails> {
        return transactionsFeeService.calculateCustomerTransactionFeeForAll()
    }

}