package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.service.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * トップコントローラ。
 */
@Controller
@RequestMapping("/top")
public class TopController {

    /**トップサービス*/
    private final TopService service;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public TopController(TopService topService, HttpSession session) {
        this.service = topService;
        this.session = session;
    }

    /**
     * トップ画面表示。
     *
     * @param account 認証されたアカウント
     * @return Path
     */
    @RequestMapping(value = "")
    public String init(@AuthenticationPrincipal Account account) {
        if (session.getAttribute(SESSION_FORM_ID) == null) {
            Account sessionAccount = service.getAccountByAccountId(account.getAccountId());
            session.setAttribute(SESSION_FORM_ID, sessionAccount);
        }
        return "top/topForm";
    }
}
