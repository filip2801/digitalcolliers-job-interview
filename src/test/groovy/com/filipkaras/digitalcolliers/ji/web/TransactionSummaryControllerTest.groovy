package com.filipkaras.digitalcolliers.ji.web

import com.filipkaras.digitalcolliers.ji.IntegrationTestSpecification
import com.filipkaras.digitalcolliers.ji.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

import java.time.LocalDateTime
import java.time.Month

class TransactionSummaryControllerTest extends IntegrationTestSpecification {

    @LocalServerPort
    int port
    @Value('${server.servlet.context-path:/}')
    String contextPath

    @Autowired
    TransactionRepository transactionRepository
    @Autowired
    CustomerRepository customerRepository
    @Autowired
    FeeWageRepository feeWageRepository
    @Autowired
    RestTemplate restTemplate

    def setup() {
        cleanDb()
        feeWageRepository.setFeeWages([new FeeWage(BigDecimal.valueOf(100), BigDecimal.valueOf(10))])
    }

    def cleanup() {
        cleanDb()
    }

    private cleanDb() {
        transactionRepository.deleteAll()
        customerRepository.deleteAll()
    }

    def "should fetch transactions fees summary for one customer"() {
        given:
        def latestDate = LocalDateTime.of(2020, Month.MARCH, 10, 15, 30, 45)
        def customerId1 = 901
        def customerId2 = 902
        transactionRepository.save(new Transaction(1, BigDecimal.valueOf(10.11), customerId1, latestDate))
        transactionRepository.save(new Transaction(2, BigDecimal.valueOf(20.5), customerId1, latestDate.minusMinutes(1)))
        customerRepository.save(new Customer(customerId1, "John", "Smith"))

        transactionRepository.save(new Transaction(3, BigDecimal.valueOf(10), customerId2, LocalDateTime.now()))

        when:
        def response = callEndpoint("/transactions/fee?customer_id=${customerId1}")

        then:
        response.statusCode == HttpStatus.OK

        def transactionFees = response.getBody()
        transactionFees.size() == 1
        transactionFees[0].customerFirstName == "John"
        transactionFees[0].customerLastName == "Smith"
        transactionFees[0].customerId == customerId1
        transactionFees[0].totalAmount == 30.61
        transactionFees[0].fee == 3.06
        transactionFees[0].numberOfTransactions == 2
        transactionFees[0].lastTransactionDate == "10.03.2020 15:30:45"
    }

    def "should fetch transactions fees summary for two customer"() {
        given:
        def latestDate = LocalDateTime.of(2020, Month.MARCH, 10, 15, 30, 45)
        def customerId1 = 901
        def customerId2 = 902
        def customerId3 = 903
        transactionRepository.save(new Transaction(1, BigDecimal.valueOf(10.11), customerId1, latestDate))
        customerRepository.save(new Customer(customerId1, "John", "Smith"))

        transactionRepository.save(new Transaction(3, BigDecimal.valueOf(200), customerId2, LocalDateTime.now()))
        customerRepository.save(new Customer(customerId2, "Ana", "Kowalski"))

        transactionRepository.save(new Transaction(4, BigDecimal.valueOf(200), customerId3, LocalDateTime.now()))
        customerRepository.save(new Customer(customerId3, "Adam", "Małysz"))

        when:
        def response = callEndpoint("/transactions/fee?customer_id=${customerId1},${customerId2}")

        then:
        response.statusCode == HttpStatus.OK

        def transactionFees = response.getBody()
        transactionFees.size() == 2
    }

    def "should fetch transactions fees summary for ALL customer"() {
        given:
        def latestDate = LocalDateTime.of(2020, Month.MARCH, 10, 15, 30, 45)
        def customerId1 = 901
        def customerId2 = 902
        transactionRepository.save(new Transaction(1, BigDecimal.valueOf(10.11), customerId1, latestDate))
        customerRepository.save(new Customer(customerId1, "John", "Smith"))

        transactionRepository.save(new Transaction(3, BigDecimal.valueOf(200), customerId2, LocalDateTime.now()))
        customerRepository.save(new Customer(customerId2, "Ana", "Kowalski"))

        when:
        def response = callEndpoint("/transactions/fee?customer_id=ALL")

        then:
        response.statusCode == HttpStatus.OK

        def transactionFees = response.getBody()
        transactionFees.size() == 2
    }

    def "should return empty list when customer does not exist"() {
        when:
        def response = callEndpoint("/transactions/fee?customer_id=1")

        then:
        response.statusCode == HttpStatus.OK
        response.getBody().isEmpty()
    }

    def "should return empty list when there are no transactions"() {
        when:
        def response = callEndpoint("/transactions/fee?customer_id=ALL")

        then:
        response.statusCode == HttpStatus.OK
        response.getBody().isEmpty()
    }

    private ResponseEntity<List> callEndpoint(String url) {
        def absoluteUrl = "http://localhost:${port}${contextPath}/${url}"

        return restTemplate.getForEntity(absoluteUrl, List)
    }

}