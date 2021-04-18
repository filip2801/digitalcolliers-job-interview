package com.filipkaras.digitalcolliers.ji.dataloader

import com.filipkaras.digitalcolliers.ji.dto.CustomerDto
import com.filipkaras.digitalcolliers.ji.dto.TransactionDto
import com.filipkaras.digitalcolliers.ji.model.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}

@Component
class TransactionCsvFileLoader(
    private val customerRepository: CustomerRepository,
    private val transactionRepository: TransactionRepository
) {

    private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

    fun load(fileName: String) {
        logger.info { "Cleaning up existing transactions" }
        transactionRepository.deleteAll()

        logger.info { "Loads transactions from file $fileName" }
        val transactions = loadTransactions(fileName)

        transactions
            .map { Transaction(it.id, it.amount, it.customerDto.id, it.date) }
            .forEach {
                transactionRepository.insert(it)
            }

        transactions
            .map { Customer(it.customerDto.id, it.customerDto.firstName, it.customerDto.lastName) }
            .forEach { customerRepository.save(it) }

        logger.info { "Transactions loaded" }
    }

    private fun loadTransactions(fileName: String): List<TransactionDto> {
        return csvReader().readAll(File(fileName))
            .drop(1)
            .map {
                TransactionDto(
                    it[0].toLong(),
                    it[1].replace(",", ".").toBigDecimal(),
                    CustomerDto(
                        it[3].toLong(),
                        it[2],
                        it[4]
                    ),
                    LocalDateTime.parse(it[5], DATE_TIME_FORMATTER)
                )
            }
    }
}