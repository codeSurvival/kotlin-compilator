package com.example.kotlincompiler.runner

import org.springframework.stereotype.Component

@Component
interface Runner {
    fun run(code: String)
}