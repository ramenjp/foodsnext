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

/**
 * アカウントコントローラ。
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    /** ユーザ登録サービス */
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 初期表示。
     *
     * @param accountRegisterForm AttributeForm
     * @return Path
     */
    @RequestMapping(value = "/register/init")
    String init(@ModelAttribute AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }

    /**
     * 確認画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult 精査結果
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/register/confirm")
    String confirm(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        if (accountService.isExistsAccountId(accountRegisterForm.getAccountId())){
            bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message!!!!!!");
            return "account/accountRegisterForm";
        }
        model.addAttribute("accountRegisterForm", accountRegisterForm);
        return "account/accountRegisterConfirmForm";
    }

    /**
     * 完了画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult 精査結果
     * @return Path
     */
    @RequestMapping(value = "/register/do", params = "register", method = RequestMethod.POST)
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
        return "account/accountRegisterCompleteForm";
    }

    /**
     * 入力画面に戻る。
     *
     * @param accountRegisterForm フォーム。
     * @return Path
     */
    @RequestMapping(value = "/register/do", params = "back", method = RequestMethod.POST)
    String back(AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }
}
