package com.lynas.springhtmx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@SpringBootApplication
class SpringHtmxApplication

fun main(args: Array<String>) {
    runApplication<SpringHtmxApplication>(*args)
}

@Controller
class IndexController {



}
