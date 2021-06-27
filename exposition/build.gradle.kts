plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.+")

    implementation(project(":application"))
}
