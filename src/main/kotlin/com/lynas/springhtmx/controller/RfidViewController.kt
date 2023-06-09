package com.lynas.springhtmx.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/rfid")
class RfidViewController {
    @GetMapping
    fun home(): String {
        return "components/rfid/rfid"
    }
}