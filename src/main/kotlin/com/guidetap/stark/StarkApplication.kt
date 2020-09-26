package com.guidetap.stark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class StarkApplication

fun main(args: Array<String>) {
    runApplication<StarkApplication>(*args)
}
