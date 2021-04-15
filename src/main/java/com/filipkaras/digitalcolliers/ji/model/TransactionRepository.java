package com.filipkaras.digitalcolliers.ji.model;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, Long> {

    @Aggregation(pipeline = {
            "{\"$match\": {\"customerId\": {\"$in\": ?0}}}",
            "{\"$group\": {" +
                    "\"_id\": \"$customerId\"," +
                    "\"totalAmount\": {\"$sum\": \"$amount\"}," +
                    "\"lastTransactionDate\": {\"$max\": \"$date\"}," +
                    "\"numberOfTransactions\": {\"$sum\": 1}" +
                    "}}"})
    List<CustomerTransactionsSummary> fetchTransactionSummaries(List<Long> customerIds);

    @Aggregation(
            "{\"$group\": {" +
                    "\"_id\": \"$customerId\"," +
                    "\"totalAmount\": {\"$sum\": \"$amount\"}," +
                    "\"lastTransactionDate\": {\"$max\": \"$date\"}," +
                    "\"numberOfTransactions\": {\"$sum\": 1}" +
                    "}}")
    List<CustomerTransactionsSummary> fetchAllTransactionSummaries();
}