package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Service

@Service
class CustomerTransactionsFeeService(
    private val transactionRepository: TransactionRepository,
    private val customerRepository: CustomerRepository,
    private val feeCalculator: FeeCalculator
) {

    fun calculateCustomerTransactionFee(customerIds: List<Long>): List<CustomerTransactionsSummaryDetails> {
        return toSummaryDetails(transactionRepository.fetchTransactionSummaries(customerIds))
    }

    fun calculateCustomerTransactionFeeForAll(): List<CustomerTransactionsSummaryDetails> {
        return toSummaryDetails(transactionRepository.fetchAllTransactionSummaries())
    }

    private fun toSummaryDetails(transactionsSummaries: List<CustomerTransactionsSummary>): List<CustomerTransactionsSummaryDetails> {
        return transactionsSummaries
            .map { Pair(it, customerRepository.findById(it.id).orElseThrow()) }
            .map {
                CustomerTransactionsSummaryDetails(
                    it.second.firstName,
                    it.second.lastName,
                    it.second.id,
                    it.first.totalAmount,
                    feeCalculator.calculateFee(it.first.totalAmount),
                    it.first.numberOfTransactions,
                    it.first.lastTransactionDate
                )
            }
    }


}