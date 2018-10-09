package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountRegisterForm;
import com.dev_training.service.AccountRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * TODO登録コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/register")
public class TodoRegisterController {

    /** TODO登録サービス */
    private final AccountRegisterService service;

    @Autowired
    public TodoRegisterController(AccountRegisterService accountRegisterService) {
        this.service = accountRegisterService;
    }

    /**
     * アカウント登録-初期表示。
     *
     * @param accountRegisterForm アカウント登録フォーム
     * @return Path
     */
    @RequestMapping(value = "/init")
    String registerInit(@ModelAttribute AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }

    /**
     * アカウント登録-確認画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @param model               モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm")
    String registerConfirm(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        if (service.isExistsAccountId(accountRegisterForm.getAccountId())) {
            bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
            return "account/accountRegisterForm";
        }
        model.addAttribute("accountRegisterForm", accountRegisterForm);
        return "account/accountRegisterConfirmForm";
    }

    /**
     * アカウント登録-完了画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "register", method = RequestMethod.POST)
    String registerComplete(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        Account account = new Account();
        account.setAccountId(accountRegisterForm.getAccountId());
        account.setName(accountRegisterForm.getName());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
        account.setEmail(accountRegisterForm.getEmail());
        service.register(account, accountRegisterForm.getPassword());
        return "account/accountRegisterCompleteForm";
    }

    /**
     * アカウント登録-入力画面に戻る。
     *
     * @param accountRegisterForm アカウント登録フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "registerBack", method = RequestMethod.POST)
    String registerBack(AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }

}
