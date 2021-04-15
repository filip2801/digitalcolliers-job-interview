package com.filipkaras.digitalcolliers.ji.model

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
interface CustomerRepository : MongoRepository<Customer, Long>