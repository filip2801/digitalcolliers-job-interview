package com.filipkaras.digitalcolliers.ji.model

import spock.lang.Specification
import spock.lang.Unroll

class FeeCalculatorTest extends Specification {

    def feeWageRepository = new FeeWageRepository()
    def feeCalculator = new FeeCalculator(feeWageRepository)

    @Unroll
    def "should calculate fees for #amount"() {
        given:
        feeWageRepository.saveAll([
                new FeeWage(BigDecimal.valueOf(10), BigDecimal.valueOf(2)),
                new FeeWage(BigDecimal.valueOf(20), BigDecimal.valueOf(1)),
                new FeeWage(BigDecimal.valueOf(30), BigDecimal.valueOf(0.5)),
        ])

        expect:
        feeCalculator.calculateFee(amount) == expectedFee

        where:
        amount                 | expectedFee
        BigDecimal.valueOf(0)  | BigDecimal.valueOf(0)
        BigDecimal.valueOf(5)  | BigDecimal.valueOf(0.1)
        BigDecimal.valueOf(10) | BigDecimal.valueOf(0.1)
        BigDecimal.valueOf(15) | BigDecimal.valueOf(0.15)
        BigDecimal.valueOf(30) | BigDecimal.valueOf(0)
        BigDecimal.valueOf(31) | BigDecimal.valueOf(0)
    }

}
