package com.example.kotlincompiler.rabbit

import com.example.kotlincompiler.runner.Runner
import org.springframework.stereotype.Component

@Component
open class CodeReceiver(private val runner: Runner) : RabbitConsumer<ByteArray> {
    override fun consume(message: ByteArray) {
        runner.run(String(message))
        println(String(message))

    }
}