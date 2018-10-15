package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountUpdateForm;
import com.dev_training.service.AccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


/**
 * アカウント情報更新コントローラ。
 */
@Controller
@RequestMapping(value = "/account/update")
public class AccountUpdateController {

    /** アカウント情報更新サービス */
    private final AccountUpdateService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountUpdateController(AccountUpdateService accountUpdateService, HttpSession session) {
        this.service = accountUpdateService;
        this.session = session;
    }

    /**
     * アカウント情報更新-初期表示。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String updateInit(Model model) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        Account targetAccount = service.getAccountById(account.getId());
        model.addAttribute("accountUpdateForm", targetAccount);
        return "account/accountUpdateForm";
    }

    /**
     * アカウント情報更新-確認画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult     精査結果
     * @param model             モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm")
    public String confirm(@Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult, Model model) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        Account targetAccount = service.getAccountById(account.getId());

        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        if (service.isNoChange(accountUpdateForm, targetAccount)) {
            bindingResult.reject("validation.noChange", "default message");
            return "account/accountUpdateForm";
        }
        String accountId = accountUpdateForm.getAccountId();
        if (!accountId.equals(targetAccount.getAccountId())) {
            if (service.isExistsAccountId(accountId)) {
                bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
                return "account/accountUpdateForm";
            }
        }
        model.addAttribute("accountUpdateForm", accountUpdateForm);
        return "account/accountUpdateConfirmForm";
    }

    /**
     * アカウント情報更新-完了画面表示。
     *
     * @param accountUpdateForm 精査済みフォーム
     * @param bindingResult     精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "update", method = RequestMethod.POST)
    public String doUpdate(@Validated AccountUpdateForm accountUpdateForm, BindingResult bindingResult) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);

        if (bindingResult.hasErrors()) {
            return "account/accountUpdateForm";
        }
        account.setAccountId(accountUpdateForm.getAccountId());
        account.setName(accountUpdateForm.getName());
        account.setEmail(accountUpdateForm.getEmail());
        account.setSelfIntroduction(accountUpdateForm.getSelfIntroduction());
        service.updateAccountById(account);
        //セッション情報の更新
        Account sessionAccount = service.getAccountById(account.getId());
        session.setAttribute(SESSION_FORM_ID, sessionAccount);
        return "account/accountUpdateCompleteForm";
    }

    /**
     * アカウント情報更新-入力画面に戻る。
     *
     * @param accountUpdateForm フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "back", method = RequestMethod.POST)
    public String back(AccountUpdateForm accountUpdateForm) {
        return "account/accountUpdateForm";
    }

}
