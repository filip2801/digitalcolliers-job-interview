package com.filipkaras.digitalcolliers.ji

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = DbInitializerInitializer )
@ActiveProfiles(["test"])
abstract class IntegrationTestSpecification extends Specification {
}
