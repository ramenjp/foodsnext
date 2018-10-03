package com.dev_training.controller;

import com.dev_training.entity.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/top")
@SessionAttributes(names = "accountName")
public class TopController {

    @RequestMapping(value = "")
    public String init(@AuthenticationPrincipal Account account, Model model) {
        model.addAttribute("accountName", account.getName());
        return "top/topForm";
    }
}
