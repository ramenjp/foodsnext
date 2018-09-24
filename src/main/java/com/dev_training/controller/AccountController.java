package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountForm;
import com.dev_training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @ModelAttribute
    public AccountForm setupForm() {
        return new AccountForm();
    }

    @RequestMapping(value = "account")
    String accountForm() {
        return "accountForm";
    }

    @RequestMapping(value = "account", method = RequestMethod.POST)
    String create(@Validated AccountForm accountForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/accountForm";
        }
        Account account = new Account();
        account.setAccountId(accountForm.getAccountId());
        account.setName(accountForm.getName());
        account.setSelfIntroduction(accountForm.getSelfIntroduction());
        account.setEmail(accountForm.getEmail());
        accountService.create(account, accountForm.getPassword());
        return "redirect:/acount/complete";
    }

    @RequestMapping(value = "account/complete", method = RequestMethod.GET)
    String createFinish() {
        return "account/accountComplete";
    }
}
