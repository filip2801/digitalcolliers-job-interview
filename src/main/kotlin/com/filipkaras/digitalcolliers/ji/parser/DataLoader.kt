package com.filipkaras.digitalcolliers.ji.parser

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val transactionCsvFileLoader: TransactionCsvFileLoader,
    @Value("\${data.transactions.path}") private val transactionFilePath: String
) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        transactionCsvFileLoader.load(transactionFilePath)
    }

}