package com.filipkaras.digitalcolliers.ji.dataloader

import com.filipkaras.digitalcolliers.ji.dto.CustomerDto
import com.filipkaras.digitalcolliers.ji.dto.TransactionDto
import com.filipkaras.digitalcolliers.ji.model.Customer
import com.filipkaras.digitalcolliers.ji.model.CustomerRepository
import com.filipkaras.digitalcolliers.ji.model.Transaction
import com.filipkaras.digitalcolliers.ji.model.TransactionRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}
private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

@Component
class TransactionCsvFileLoader(
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository
) {

    fun load(fileName: String) {
        logger.info { "Cleaning up existing transactions" }
        transactionRepository.deleteAll()

        logger.info { "Loads transactions from file $fileName" }
        val transactionsDtos = loadTransactions(fileName)

        saveTransactions(transactionsDtos)
        saveCustomers(transactionsDtos)

        logger.info { "Transactions loaded" }
    }

    private fun loadTransactions(fileName: String): List<TransactionDto> {
        return csvReader().readAll(File(fileName))
            .drop(1)
            .map {
                TransactionDto(
                    it[0].toLong(),
                    toBigDecimal(it[1]),
                    CustomerDto(
                        it[3].toLong(),
                        it[2],
                        it[4]
                    ),
                    toLocalDateTime(it[5])
                )
            }
    }

    private fun saveTransactions(transactionsDtos: List<TransactionDto>) {
        val customerTransactions = transactionsDtos
            .map { Transaction(it.id, it.amount, it.customerDto.id, it.date) }

        transactionRepository.saveAll(customerTransactions)
    }

    private fun saveCustomers(transactionsDtos: List<TransactionDto>) {
        val customers = transactionsDtos
            .map { Customer(it.customerDto.id, it.customerDto.firstName, it.customerDto.lastName) }
            .toSet()

        customerRepository.saveAll(customers)
    }

    private fun toBigDecimal(value: String) = value.replace(",", ".").toBigDecimal()

    private fun toLocalDateTime(value: String) = LocalDateTime.parse(value, DATE_TIME_FORMATTER)

}