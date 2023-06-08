package com.lynas.springhtmx

import io.github.wimdeblauwe.hsbt.mvc.HtmxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxRefresh
import io.github.wimdeblauwe.hsbt.mvc.HxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxTrigger
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


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

    @GetMapping("/userList")
    fun store(model: Model): String {
        model.addAttribute("users", userList)
        return "userList"
    }

    @GetMapping("/users/byId/{userId}")
    @HxTrigger("openSidebar")
    fun getUserById(@PathVariable userId: String, model: Model): String {
        println("get user by id $userId")
        model.addAttribute("user",
            userList.find { it.id == userId } ?: User(name = "Not Found"))
        return "userDetails"
    }
}

@RestController
class ApiController {
    @PostMapping("/clicked")
    fun clicked() = "clicked data"

    @GetMapping("/store")
    @HxRequest
    fun store(details: HtmxRequest): String {
        return "stored data"
    }

    @PostMapping("/validate")
    @HxRequest
    fun validate(request: HtmxRequest, @RequestParam title: String): String {
        return if (title.length < 3) "Invalid input" else ""
    }
}

data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int = 0,
    val location: String = ""
)

var userList = mutableListOf(
    User(name = "John Doe", age = 34, location = "Dhaka"),
    User(name = "Max Payne", age = 65, location = "New York"),
    User(name = "Uzumaki Naruto", age = 17, location = "Munich"),
    User(name = "John Constantine", age = 45, location = "London"),
)

@RestController
@RequestMapping("/users")
class UserController {
    @GetMapping
    fun userList(): MutableList<User> {
        println(userList.size)
        println("get user list")
        return userList
    }

    @PostMapping("/validateName")
    @HxRequest
    fun validateUserName(@RequestParam name: String): String {
        return if (name.length < 3) "Invalid input" else ""
    }

    @PostMapping
    @HxRequest
    @HxTrigger("updateUserList")
    fun createNewUser(
        @ModelAttribute user: User,
        request: HtmxRequest,
        response: HttpServletResponse
    ): String {
        userList.add(user)
        println(userList.size)
        response.setHeader("HX-Trigger", "updateUserList");
        return user.toString()
    }
}