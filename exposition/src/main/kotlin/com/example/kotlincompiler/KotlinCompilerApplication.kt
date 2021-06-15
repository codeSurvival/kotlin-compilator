package com.example.kotlincompiler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinCompilerApplication

fun main(args: Array<String>) {
    runApplication<KotlinCompilerApplication>(*args)

}
