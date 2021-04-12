package com.filipkaras.digitalcolliers.ji

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(["test"])
abstract class IntegrationTestSpecification extends Specification {
}
