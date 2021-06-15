package com.example.kotlincompiler.rabbit

interface RabbitConsumer<T> {
    fun consume(message: T)
}