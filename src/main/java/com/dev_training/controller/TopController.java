package com.dev_training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("top")
public class TopController {
    @RequestMapping(value = "")
    public String loginForm() {
        return "top/topForm";
    }
}
