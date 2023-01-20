dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    testImplementation(project(":sloth:sloth-test-utils"))
    testImplementation(project(":envy:envy-test-utils"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("org.testcontainers:mongodb:1.17.6")
}
