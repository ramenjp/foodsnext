package com.dev_training.controller27;


import com.dev_training.common.CodeValue;
import com.dev_training.entity27.Account;
import com.dev_training.service27.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * トップコントローラ。
 */
@Controller
@RequestMapping("/top")
public class TopController {

    /**
     * トップサービス
     */
    private final TopService service;
    /**
     * HTTPセッション
     */
    private final HttpSession session;
    /**
     * セッションキー(ログインユーザのアカウント)
     */
    private static final String SESSION_FORM_ID = "account";


    /**
     * コード値
     */
    private final CodeValue codeValue;

    @Autowired
    public TopController(TopService topService, HttpSession session, CodeValue codeValue) {
        this.service = topService;
        this.session = session;
        this.codeValue = codeValue;
    }

    /**
     * ログイン成功時処理。
     *
     * @return Path
     */
    @RequestMapping(value = "loginSuccess")
    public String loginSuccess() {
        return "redirect:/top";
    }

    /**
     * トップ画面表示。
     *
     * @param account 認証されたアカウント
     * @param model   モデル
     * @return Path
     */
    @RequestMapping(value = "")
    public String init(@AuthenticationPrincipal Account account, Model model) {
        // 初回のアクセスなら、アカウントを検索してセッションに格納する
        if (Objects.isNull(session.getAttribute(SESSION_FORM_ID))) {
            Account sessionAccount = service.getAccountById(account.getAccountId());
            session.setAttribute(SESSION_FORM_ID, sessionAccount);
        }
        return "top/topForm";
    }

    @RequestMapping(value = "/matching1", params = "match", method = RequestMethod.POST)
    public String match() {
        return "matching_count";
    }

    @RequestMapping(value = "/history", params = "historyCheck", method = RequestMethod.POST)
    public String historyCheck() {
        return "historyCheck";
    }

    @RequestMapping(value = "/setting", params = "accountUpdate", method = RequestMethod.POST)
    public String accountUpdate() {
        return "accountUpdateForm";
    }


}