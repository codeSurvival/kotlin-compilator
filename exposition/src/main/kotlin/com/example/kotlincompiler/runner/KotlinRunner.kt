package com.example.kotlincompiler.runner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
open class KotlinRunner(
    @Autowired private val compilator: Compilator,
) : Runner {

    override fun run(code: String, userId: String, turnObjective: Int) {

        val compilatorPaths = compilator.buildEntrypoint()
        compilator.addUserCode(code, compilatorPaths)
        compilator.compileAndExecute(compilatorPaths, turnObjective, userId)
        compilator.clean(compilatorPaths)
    }
}