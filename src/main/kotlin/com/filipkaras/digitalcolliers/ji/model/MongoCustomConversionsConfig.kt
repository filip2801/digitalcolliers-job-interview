package com.filipkaras.digitalcolliers.ji.model

import org.bson.types.Decimal128
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.math.BigDecimal
import org.springframework.data.convert.WritingConverter
import org.springframework.data.convert.ReadingConverter

@Configuration
class MongoCustomConversionsConfig {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(
            listOf(
                BigDecimalDecimal128Converter(),
                Decimal128BigDecimalConverter()
            )
        )
    }

}

@WritingConverter
class BigDecimalDecimal128Converter : Converter<BigDecimal?, Decimal128> {
    override fun convert(source: BigDecimal): Decimal128 {
        return Decimal128(source)
    }
}

@ReadingConverter
private class Decimal128BigDecimalConverter : Converter<Decimal128, BigDecimal> {
    override fun convert(source: Decimal128): BigDecimal {
        return source.bigDecimalValue()
    }
}
