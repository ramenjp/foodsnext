package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountUpdateForm;
import com.dev_training.service.AccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * アカウント情報変更コントローラ。
 */
@Controller
@RequestMapping(value = "/account/update")
public class AccountUpdateController {

    /** アカウント情報変更サービス */
    private final AccountUpdateService service;

    @Autowired
    public AccountUpdateController(AccountUpdateService accountUpdateService) {
        this.service = accountUpdateService;
    }

    /**
     * アカウント情報変更-初期表示。
     *
     * @param account 更新対象のアカウント
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    String updateInit(@AuthenticationPrincipal Account account, Model model) {
        AccountUpdateForm tar = new AccountUpdateForm();
        tar.setAccountId(account.getAccountId());
        tar.setSelfIntroduction(account.getSelfIntroduction());
        tar.setName(account.getName());
        tar.setEmail(account.getEmail());
        model.addAttribute("accountUpdateForm",tar);
        return "account/accountUpdateForm";
    }

    /**
     * アカウント情報変更-確認画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult 精査結果
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm")
    String confirm(@Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult, @AuthenticationPrincipal Account account, Model model) {
        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        String accountId = accountUpdateForm.getAccountId();
        if (!accountId.equals(account.getAccountId()) ) {
            if (service.isExistsAccountId(accountId)){
                bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message!!!!!!");
                return "account/accountUpdateForm";
            }
        }
        model.addAttribute("accountUpdateForm", accountUpdateForm);
        return "account/accountUpdateConfirmForm";
    }

    /**
     * アカウント情報変更-完了画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult 精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "update", method = RequestMethod.POST)
    String doUpdate(@Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult, @AuthenticationPrincipal Account account) {
        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        Account targetAccount = service.getAccountById(account.getId());
        targetAccount.setAccountId(accountUpdateForm.getAccountId());
        targetAccount.setName(accountUpdateForm.getName());
        targetAccount.setEmail(accountUpdateForm.getEmail());
        targetAccount.setSelfIntroduction(accountUpdateForm.getSelfIntroduction());
        service.updateAccountById(targetAccount);
        return "account/accountUpdateCompleteForm";
    }

    /**
     * アカウント情報変更-入力画面に戻る。
     *
     * @param accountUpdateForm フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "back", method = RequestMethod.POST)
    String back(AccountUpdateForm accountUpdateForm) {
        return "account/accountUpdateForm";
    }

}
