package com.dev_training.controller;


import com.dev_training.entity.Account;
import com.dev_training.form.AccountPasswordUpdateForm;
import com.dev_training.service.AccountPasswordUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**password
 * パスワード更新コントローラ。
 */
@Controller
@RequestMapping(value = "/account/password")
public class AccountPasswordUpdateController {

    /** パスワード更新フォーム */
    private AccountPasswordUpdateService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountPasswordUpdateController(AccountPasswordUpdateService accountPasswordUpdateService, HttpSession session) {
        this.service = accountPasswordUpdateService;
        this.session = session;
    }


    /**
     * パスワード更新-初期表示。
     *accountPasswordUpdateForm
     * @param accountPasswordUpdateForm パスワード更新フォーム
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String registerInit(@ModelAttribute AccountPasswordUpdateForm accountPasswordUpdateForm) {
        return "account/accountPasswordUpdateForm";
    }

    /**
     * パスワード更新-確認画面表示。
     *
     * @param accountPasswordUpdateForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @param model               モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String registerConfirm(@ModelAttribute @Validated AccountPasswordUpdateForm accountPasswordUpdateForm, BindingResult bindingResult, Model model) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "account/accountPasswordUpdateForm";
        }
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        boolean isValid = service.validCurrentPassword(account.getId(), accountPasswordUpdateForm.getCurrentPassword());
        if (!isValid) {
            bindingResult.reject("validation.current.password","default message");
            return "account/accountPasswordUpdateForm";
        }
        service.updatePassword(account.getId(), accountPasswordUpdateForm.getNewPassword());
        return "account/accountPasswordUpdateCompleteForm";
    }

}
