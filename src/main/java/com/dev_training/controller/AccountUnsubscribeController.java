package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.service.AccountUnsubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * 退会コントローラ。
 */
@Controller
@RequestMapping(value = "/account/unsubscribe")
public class AccountUnsubscribeController {

    /** 退会サービス */
    private final AccountUnsubscribeService service;
    /** HTTP SESSION */
    private final HttpSession session;

    @Autowired
    public AccountUnsubscribeController(AccountUnsubscribeService accountUnsubscribeService, HttpSession session) {
        this.service = accountUnsubscribeService;
        this.session = session;
    }
    /**
     * 退会-初期表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String unsubscribeInit() {
        return "account/accountUnsubscribeForm";
    }

    /**
     * 退会-確認画面表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/confirm",  method = RequestMethod.POST)
    public String unsubscribeConfirm() {
        return "account/accountUnsubscribeConfirmForm";
    }

    /**
     * 退会-完了画面表示。
     *
     * @param account 認証済みのアカウントEntity。
     * @return Path
     */
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public String unsubscribeComplete(@AuthenticationPrincipal Account account) {
        // 削除処理
        service.delete(account.getId());
        // セッション破棄
        session.invalidate();
        return "account/accountUnsubscribeCompleteForm";
    }
}