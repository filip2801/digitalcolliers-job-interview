package com.filipkaras.digitalcolliers.ji.model

import com.filipkaras.digitalcolliers.ji.IntegrationTestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@DirtiesContext
class TransactionRepositoryTest extends IntegrationTestSpecification {

    @Autowired
    TransactionRepository transactionRepository

    def setup() {
        cleanDb()
    }

    def cleanup() {
        cleanDb()
    }

    private cleanDb() {
        transactionRepository.deleteAll()
    }

    def "should save transaction"() {
        given:
        def date = LocalDateTime.now()
        def customerId = 901
        def transactionId = 101
        def amount = BigDecimal.valueOf(10.11)

        when:
        transactionRepository.save(new Transaction(transactionId, amount, customerId, date))

        then:
        def transaction = transactionRepository.findById(101).get()
        transaction.amount == amount
        transaction.customerId == customerId
        transaction.date == date.truncatedTo(ChronoUnit.MILLIS)
    }

    def "should fetch transactions summary for one customer"() {
        given:
        def latestDate = LocalDateTime.now()
        def customerId1 = 901
        def customerId2 = 902
        transactionRepository.save(new Transaction(101, BigDecimal.valueOf(10.11), customerId1, latestDate))
        transactionRepository.save(new Transaction(102, BigDecimal.valueOf(20.5), customerId1, latestDate.minusMinutes(1)))

        // different customer
        transactionRepository.save(new Transaction(103, BigDecimal.valueOf(10), customerId2, LocalDateTime.now()))

        when:
        def summaries = transactionRepository.fetchTransactionSummaries([customerId1])

        then:
        summaries.size() == 1
        summaries.first().id == customerId1
        summaries.first().totalAmount == BigDecimal.valueOf(30.61)
        summaries.first().numberOfTransactions == 2
        summaries.first().lastTransactionDate == latestDate.truncatedTo(ChronoUnit.MILLIS)
    }

    def "should fetch transactions summary for multiple customers"() {
        given:
        def latestDate1 = LocalDateTime.now()
        def latestDate2 = LocalDateTime.now().minusMinutes(10)
        def customerId1 = 901
        def customerId2 = 902
        def customerId3 = 903
        transactionRepository.save(new Transaction(101, BigDecimal.valueOf(10.11), customerId1, latestDate1))
        transactionRepository.save(new Transaction(102, BigDecimal.valueOf(20.5), customerId1, latestDate1.minusMinutes(1)))

        transactionRepository.save(new Transaction(103, BigDecimal.valueOf(10.1), customerId2, latestDate2.minusMinutes(1)))
        transactionRepository.save(new Transaction(104, BigDecimal.valueOf(20.1), customerId2, latestDate2))
        transactionRepository.save(new Transaction(105, BigDecimal.valueOf(30), customerId2, latestDate2.minusMinutes(10)))

        transactionRepository.save(new Transaction(106, BigDecimal.valueOf(30), customerId3, latestDate2.minusMinutes(10)))

        when:
        def summaries = transactionRepository.fetchTransactionSummaries([customerId1, customerId2])

        then:
        summaries.size() == 2
        def summariesCustomer1 = summaries.find { it.id == customerId1 }
        summariesCustomer1.totalAmount == BigDecimal.valueOf(30.61)
        summariesCustomer1.numberOfTransactions == 2
        summariesCustomer1.lastTransactionDate == latestDate1.truncatedTo(ChronoUnit.MILLIS)

        def summariesCustomer2 = summaries.find { it.id == customerId2 }
        summariesCustomer2.totalAmount == BigDecimal.valueOf(60.2)
        summariesCustomer2.numberOfTransactions == 3
        summariesCustomer2.lastTransactionDate == latestDate2.truncatedTo(ChronoUnit.MILLIS)
    }

    def "should fetch transactions summary for all customers"() {
        given:
        def latestDate1 = LocalDateTime.now()
        def latestDate2 = LocalDateTime.now().minusMinutes(10)
        def customerId1 = 901
        def customerId2 = 902
        transactionRepository.save(new Transaction(101, BigDecimal.valueOf(10.11), customerId1, latestDate1))
        transactionRepository.save(new Transaction(102, BigDecimal.valueOf(20.5), customerId1, latestDate1.minusMinutes(1)))

        transactionRepository.save(new Transaction(103, BigDecimal.valueOf(10.1), customerId2, latestDate2.minusMinutes(1)))
        transactionRepository.save(new Transaction(104, BigDecimal.valueOf(20.1), customerId2, latestDate2))
        transactionRepository.save(new Transaction(105, BigDecimal.valueOf(30), customerId2, latestDate2.minusMinutes(10)))

        when:
        def summaries = transactionRepository.fetchAllTransactionSummaries()

        then:
        summaries.size() == 2
        def summariesCustomer1 = summaries.find { it.id == customerId1 }
        summariesCustomer1.totalAmount == BigDecimal.valueOf(30.61)
        summariesCustomer1.numberOfTransactions == 2
        summariesCustomer1.lastTransactionDate == latestDate1.truncatedTo(ChronoUnit.MILLIS)

        def summariesCustomer2 = summaries.find { it.id == customerId2 }
        summariesCustomer2.totalAmount == BigDecimal.valueOf(60.2)
        summariesCustomer2.numberOfTransactions == 3
        summariesCustomer2.lastTransactionDate == latestDate2.truncatedTo(ChronoUnit.MILLIS)
    }

    def "should fetch empty collections when there are no transactions for given customerId"() {
        expect:
        transactionRepository.fetchTransactionSummaries([1]).isEmpty()
    }

    def "should fetch empty collections when there are no transactions"() {
        expect:
        transactionRepository.fetchAllTransactionSummaries().isEmpty()
    }

}
