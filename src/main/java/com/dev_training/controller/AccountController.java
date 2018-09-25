package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountRegisterForm;
import com.dev_training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    /** ユーザ登録サービス */
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/register/init")
    String accountForm(@ModelAttribute AccountRegisterForm accountRegisterForm) {
        return "accountForm";
    }

    @RequestMapping(value = "/register/do", method = RequestMethod.POST)
    String register(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/accountForm";
        }
        Account account = new Account();
        account.setAccountId(accountRegisterForm.getAccountId());
        account.setName(accountRegisterForm.getName());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
        account.setEmail(accountRegisterForm.getEmail());
        accountService.create(account, accountRegisterForm.getPassword());
        return "redirect:/login";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String createFinish() {
        return "account/accountComplete";
    }
}
