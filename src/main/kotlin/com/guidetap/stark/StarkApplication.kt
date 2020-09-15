package com.guidetap.stark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StarkApplication

fun main(args: Array<String>) {
    runApplication<StarkApplication>(*args)
}
