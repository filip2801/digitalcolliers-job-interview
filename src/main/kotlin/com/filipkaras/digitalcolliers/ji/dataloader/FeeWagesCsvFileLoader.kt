package com.filipkaras.digitalcolliers.ji.dataloader

import com.filipkaras.digitalcolliers.ji.model.FeeWage
import com.filipkaras.digitalcolliers.ji.model.FeeWageRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.stereotype.Component
import java.io.File

@Component
class FeeWagesCsvFileLoader(
    private val feeWageRepository: FeeWageRepository
) {

    fun load(fileName: String) {
        feeWageRepository.saveAll(loadFeeWages(fileName))
    }

    private fun loadFeeWages(fileName: String): List<FeeWage> {
        return csvReader().readAll(File(fileName))
            .drop(1)
            .map {
                FeeWage(
                    it[0].replace(",", ".").toBigDecimal(),
                    it[1].replace(",", ".").toBigDecimal()
                )
            }
    }
}