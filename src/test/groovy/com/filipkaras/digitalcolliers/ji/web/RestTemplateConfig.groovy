package com.filipkaras.digitalcolliers.ji.web

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@TestConfiguration
class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        new RestTemplate()
    }

}