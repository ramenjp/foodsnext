package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountRegisterForm;
import com.dev_training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "account/accountRegisterForm";
    }

    @RequestMapping(value = "/register/do", method = RequestMethod.POST)
    String doRegister(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        Account account = new Account();
        account.setAccountId(accountRegisterForm.getAccountId());
        account.setName(accountRegisterForm.getName());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
        account.setEmail(accountRegisterForm.getEmail());
        accountService.create(account, accountRegisterForm.getPassword());
        return "redirect:/login";
    }
//
//    @RequestMapping("/input/back")
//    public String inputBack(CountryForm countryForm
//            , RedirectAttributes redirectAttributes) {
//
//        redirectAttributes.addFlashAttribute("countryForm", countryForm);
//        return "redirect:/country/input";
//    }
//
//    @RequestMapping("/register/confirm")
//    public String registerConfirm(@Validated AccountRegisterForm countryForm
//            , BindingResult bindingResult
//            , Model model) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("validationError", "不正な値が入力されました。");
//            return accountForm();
//        }
//
//        return "country/confirm";
//    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    String createFinish() {
        return "account/accountComplete";
    }
}
