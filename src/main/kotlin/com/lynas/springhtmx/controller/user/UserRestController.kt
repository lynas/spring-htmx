package com.lynas.springhtmx.controller.user

import io.github.wimdeblauwe.hsbt.mvc.HxRequest
import io.github.wimdeblauwe.hsbt.mvc.HxTrigger
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
    fun validateUserName(@RequestParam name: String): String {
        println(name)
        return if (name.length < 3) "Invalid input" else ""
    }

    @PostMapping
    @HxTrigger("updateUserList")
    fun createNewUser(
        @ModelAttribute user: User,
    ): String {
        userList.add(user)
        return user.toString()
    }
}