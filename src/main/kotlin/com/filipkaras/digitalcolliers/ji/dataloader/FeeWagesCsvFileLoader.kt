package com.filipkaras.digitalcolliers.ji.dataloader

import com.filipkaras.digitalcolliers.ji.model.FeeWage
import com.filipkaras.digitalcolliers.ji.model.FeeWageRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File

private val logger = KotlinLogging.logger {}

@Component
class FeeWagesCsvFileLoader(
    private val feeWageRepository: FeeWageRepository
) {

    fun load(fileName: String) {
        logger.info { "Loads fee wages from file $fileName" }
        feeWageRepository.setFeeWages(loadFeeWages(fileName))
        logger.info { "Fee wages loaded" }
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