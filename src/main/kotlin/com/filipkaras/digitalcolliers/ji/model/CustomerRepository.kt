package com.filipkaras.digitalcolliers.ji.model

import org.springframework.stereotype.Component

@Component
class CustomerRepository {
    val storage: HashMap<Long, Customer> = hashMapOf()

    fun save(customer: Customer) {
        storage[customer.id] = customer
    }

    fun findAll(): List<Customer> {
        return storage.values.toList()
    }
}