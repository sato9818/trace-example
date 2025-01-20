package com.example.trace.grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcSpringTraceApplication

fun main(args: Array<String>) {
    runApplication<GrpcSpringTraceApplication>(args = args)
}