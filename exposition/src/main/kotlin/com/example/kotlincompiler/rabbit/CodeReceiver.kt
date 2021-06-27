package com.example.kotlincompiler.rabbit

import com.example.kotlincompiler.dtos.CodeParametersDTO
import com.example.kotlincompiler.runner.Runner
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
open class CodeReceiver(private val runner: Runner) : RabbitConsumer<ByteArray> {


    override fun consume(message: ByteArray) {
        val m = jacksonObjectMapper()
        val codeParameter = m.readValue<CodeParametersDTO>(String(message))
        runner.run(codeParameter.code, codeParameter.userId, codeParameter.turnObjective)
    }
}