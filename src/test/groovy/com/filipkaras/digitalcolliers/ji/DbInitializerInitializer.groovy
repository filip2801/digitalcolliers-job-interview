package com.filipkaras.digitalcolliers.ji

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer

class DbInitializerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        def username = "mongoadmin"
        def password = "secret"

        def mongoDbContainer = new GenericContainer("mongo:4.4.5")
                .withExposedPorts(27017)
                .withEnv([
                        MONGO_INITDB_ROOT_USERNAME: username,
                        MONGO_INITDB_ROOT_PASSWORD: password])

        mongoDbContainer.start()

        TestPropertyValues.of(
                "spring.data.mongodb.host=${mongoDbContainer.host}",
                "spring.data.mongodb.port=${mongoDbContainer.firstMappedPort}",
                "spring.data.mongodb.username=${username}",
                "spring.data.mongodb.password=${password}",
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

}
