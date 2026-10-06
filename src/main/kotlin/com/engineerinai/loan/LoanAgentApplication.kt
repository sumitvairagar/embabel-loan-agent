package com.engineerinai.loan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoanAgentApplication

fun main(args: Array<String>) {
    runApplication<LoanAgentApplication>(*args)
}
