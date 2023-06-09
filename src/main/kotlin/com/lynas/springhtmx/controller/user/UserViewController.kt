package com.lynas.springhtmx.controller.user

import io.github.wimdeblauwe.hsbt.mvc.HxTrigger
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/views/users")
class UserViewController {
    @GetMapping("/userList")
    fun store(model: Model): String {
        model.addAttribute("users", userList)
        return "components/users/userList"
    }

    @GetMapping("/byId/{userId}")
    @HxTrigger("openSidebar")
    fun getUserById(@PathVariable userId: String, model: Model): String {
        model.addAttribute("user",
            userList.find { it.id == userId } ?: User(name = "Not Found"))
        return "components/users/userDetails"
    }

    @GetMapping("/createUserForm")
    fun createUserForm(): String {
        return "components/users/createUserForm"
    }
}