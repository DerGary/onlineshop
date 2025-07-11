package dev.gerasch.onlineshop

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "world") name: String): String {
        return "Hello $name!"
    }
}
