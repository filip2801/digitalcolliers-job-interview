package com.filipkaras.digitalcolliers.ji.dataloader

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val transactionCsvFileLoader: TransactionCsvFileLoader,
    private val feeWagesCsvFileLoader: FeeWagesCsvFileLoader,
    @Value("\${data.transactions.path}") private val transactionFilePath: String,
    @Value("\${data.feeWages.path}") private val feeWagesFilePath: String
) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        transactionCsvFileLoader.load(transactionFilePath)
        feeWagesCsvFileLoader.load(feeWagesFilePath)
    }

}