package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Component

@Component
class TransactionRepository {
    private val storage: HashMap<Long, Transaction> = hashMapOf()

    fun save(transaction: Transaction) {
        storage[transaction.id] = transaction
    }

    fun findAll(): List<Transaction> {
        return storage.values.toList()
    }
}