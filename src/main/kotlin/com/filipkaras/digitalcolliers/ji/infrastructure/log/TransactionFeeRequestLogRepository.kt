package com.filipkaras.digitalcolliers.ji.infrastructure.log

import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionFeeRequestLogRepository: MongoRepository<TransactionFeeRequestLog, Long>