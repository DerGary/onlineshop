package dev.gerasch.onlineshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class OnlineShopApplication

fun main(args: Array<String>) {
    runApplication<OnlineShopApplication>(*args)
}
