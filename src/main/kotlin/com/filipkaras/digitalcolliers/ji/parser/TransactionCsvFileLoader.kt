package com.filipkaras.digitalcolliers.ji.parser

import com.filipkaras.digitalcolliers.ji.dto.CustomerDto
import com.filipkaras.digitalcolliers.ji.dto.TransactionDto
import com.filipkaras.digitalcolliers.ji.model.Customer
import com.filipkaras.digitalcolliers.ji.model.CustomerRepository
import com.filipkaras.digitalcolliers.ji.model.Transaction
import com.filipkaras.digitalcolliers.ji.model.TransactionRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TransactionCsvFileLoader(
    private val transactionRepository: TransactionRepository,
    private val customerRepository: CustomerRepository
) {

    private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

    fun load(fileName: String) {
        val transactions = loadTransactions(fileName)

        transactions
            .map { Transaction(it.id, it.amount, it.customerDto.id, it.date) }
            .forEach { transactionRepository.save(it) }

        transactions
            .map { Customer(it.customerDto.id, it.customerDto.firstName, it.customerDto.lastName) }
            .forEach { customerRepository.save(it) }
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