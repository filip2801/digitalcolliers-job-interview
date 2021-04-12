package com.filipkaras.digitalcolliers.ji

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext

class DigitalColliersJobInterviewApplicationIT extends IntegrationTestSpecification {

    @Autowired
    WebApplicationContext context

    def "should start application"() {
        expect: 'application started'
        context != null
    }

}
