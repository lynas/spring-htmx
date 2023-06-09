package com.lynas.springhtmx.controller.user

import io.github.wimdeblauwe.hsbt.mvc.HtmxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxTrigger
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserRestController {
    @GetMapping
    fun userList(): MutableList<User> {
        println(userList.size)
        println("get user list")
        return userList
    }

    @PostMapping("/validateName")
    @HxRequest
    fun validateUserName(@RequestParam name: String): String {
        println(name)
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