package com.lynas.springhtmx

import io.github.wimdeblauwe.hsbt.mvc.HtmxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxRefresh
import io.github.wimdeblauwe.hsbt.mvc.HxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxTrigger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
	fun index(model: Model): String {
		model.addAttribute("users", userList)
		return "index"
	}

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

@RestController
@RequestMapping("/users")
class UserController{
	@GetMapping
	fun userList() = userList

	@PostMapping("/validateName")
	@HxRequest
	fun validateUserName(@RequestParam name: String) : String {
		return if (name.length < 3) "Invalid input" else ""
	}

	@PostMapping
	@HxRequest
	fun createNewUser(@ModelAttribute user: User) : String {
		println(user)
		return user.toString()
	}
}