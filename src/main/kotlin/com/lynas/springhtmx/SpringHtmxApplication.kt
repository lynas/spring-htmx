package com.lynas.springhtmx

import io.github.wimdeblauwe.hsbt.mvc.HtmxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxRequest
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class SpringHtmxApplication

fun main(args: Array<String>) {
	runApplication<SpringHtmxApplication>(*args)
}

@Controller
class IndexController {

	@GetMapping("/")
	fun index() = "index"

	@GetMapping("/users")
	@HxRequest
	fun htmxRequest(details: HtmxRequest, model: Model): String {
		println(details)
		model.addAttribute("users", userList)
		return "users"
	}
}

@RestController
class ApiController{
	@PostMapping("/clicked")
	fun clicked() = "clicked data"

	@PostMapping("/store")
	@HxRequest
	fun store(details: HtmxRequest) : String {
		return "stored data"
	}

	@PostMapping("/validate")
	@HxRequest
	fun validate(request: HtmxRequest, @RequestParam title: String) : String {
		return if (title.length < 3) "Invalid input" else ""
	}
}

data class User(
	val name: String
)

var userList = mutableListOf(
	User("Name1"),
	User("Name2")
)
