package com.filipkaras.digitalcolliers.ji

import com.filipkaras.digitalcolliers.ji.web.RestTemplateConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestTemplateConfig)
@ContextConfiguration(initializers = DbInitializerInitializer )
@ActiveProfiles(["test"])
abstract class IntegrationTestSpecification extends Specification {
}
