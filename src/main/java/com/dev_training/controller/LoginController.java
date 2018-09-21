package com.dev_training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {
    @RequestMapping(value="")
    public String loginForm() {
        return "loginForm";
    }
}
