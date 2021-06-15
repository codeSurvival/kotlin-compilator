plugins {
    kotlin("jvm")
}

tasks.jar {
    archiveFileName.set("plugin.jar")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":compilator:game-pdk"))
}
