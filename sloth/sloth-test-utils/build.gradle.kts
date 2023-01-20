dependencies {
    implementation(project(":envy:envy-domain"))
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    implementation("org.testcontainers:mongodb:1.17.6")
    implementation("io.kotest:kotest-assertions-core:5.5.4")
    implementation("io.kotest:kotest-property:5.5.4")
    api("io.kotest:kotest-runner-junit5:5.5.4")
    api("io.github.serpro69:kotlin-faker:1.13.0")
    api("com.tngtech.archunit:archunit:1.0.1")
    api("com.tngtech.archunit:archunit-junit5:1.0.1")
}
