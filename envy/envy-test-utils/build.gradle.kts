dependencies {
    implementation(project(":sloth:sloth-test-utils"))
    implementation(project(":envy:envy-domain")) {
        exclude("org.springframework.boot")
    }
}
