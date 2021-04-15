package com.filipkaras.digitalcolliers.ji.dataloader

import com.filipkaras.digitalcolliers.ji.IntegrationTestSpecification
import com.filipkaras.digitalcolliers.ji.model.FeeWageRepository
import org.springframework.beans.factory.annotation.Autowired

class FeeWagesDataLoaderTest extends IntegrationTestSpecification {

    @Autowired
    FeeWageRepository feeWageRepository

    def "should load transactions from file"() {
        expect:
        def feeWages = feeWageRepository.findAllOrderedByAmountLessThan()
        feeWages[0].transactionValueLessThan == BigDecimal.valueOf(1000)
        feeWages[0].feePercentage == BigDecimal.valueOf(3.5)

        feeWages[1].transactionValueLessThan == BigDecimal.valueOf(2500)
        feeWages[1].feePercentage == BigDecimal.valueOf(2.5)

        feeWages[2].transactionValueLessThan == BigDecimal.valueOf(5000)
        feeWages[2].feePercentage == BigDecimal.valueOf(1.1)

        feeWages[3].transactionValueLessThan == BigDecimal.valueOf(10000)
        feeWages[3].feePercentage == BigDecimal.valueOf(0.1)

        feeWages[4].transactionValueLessThan == BigDecimal.valueOf(20000.5)
        feeWages[4].feePercentage == BigDecimal.valueOf(0.01)
    }
}
