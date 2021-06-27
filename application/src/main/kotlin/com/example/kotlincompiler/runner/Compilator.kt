package com.example.kotlincompiler.runner

interface Compilator {
    fun compileAndExecute(compilatorPaths: CompilatorPaths, turnObjective: Int, userId: String)
    fun buildEntrypoint(): CompilatorPaths
    fun clean(compilatorPaths: CompilatorPaths)
    fun addUserCode(userCode: String, compilatorPaths: CompilatorPaths)
}