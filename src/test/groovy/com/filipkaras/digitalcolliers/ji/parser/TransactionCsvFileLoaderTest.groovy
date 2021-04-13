package com.filipkaras.digitalcolliers.ji.parser

import com.filipkaras.digitalcolliers.ji.model.CustomerRepository
import com.filipkaras.digitalcolliers.ji.model.TransactionRepository
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

class TransactionCsvFileLoaderTest extends Specification {

    def transactionRepository = new TransactionRepository()
    def customerRepository = new CustomerRepository()

    def transactionCsvFileLoader = new TransactionCsvFileLoader(transactionRepository, customerRepository)

    def "should load transactions from file"() {
        when:
        transactionCsvFileLoader.load("src/test/resources/transactions-test.csv")

        then:
        def transactions = transactionRepository.findAll()
        transactions.size() == 4

        def transaction101 = transactions.find { it.id == 101 }
        transaction101 != null
        transaction101.amount == BigDecimal.valueOf(111.11)
        transaction101.customerId == 1
        transaction101.date == LocalDateTime.of(2020, Month.DECEMBER, 11, 14, 57, 22)

        transactions.find { it.id == 102 } != null
        transactions.find { it.id == 103 } != null
        transactions.find { it.id == 104 } != null

        and:
        def customers = customerRepository.findAll()
        customers.size() == 3

        def customer1 = customers.find { it.id == 1 }
        customer1 != null
        customer1.firstName == "Andrzej"
        customer1.lastName == "Andrzejowski"

        customers.find { it.id == 2 } != null
        customers.find { it.id == 3 } != null
    }
}
