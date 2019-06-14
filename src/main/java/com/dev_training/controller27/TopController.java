package com.dev_training.controller27;


import com.dev_training.common.CodeValue;
import com.dev_training.entity27.Account;
import com.dev_training.form27.HistoryForm;
import com.dev_training.service27.HistoryService;
import com.dev_training.service27.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


/**
 * トップコントローラ。
 */
@Controller
@RequestMapping("/top")
public class TopController {

    /**トップサービス*/
    private final TopService service;

    /**Historyサービス*/
    private final HistoryService historyService;

    /**HTTPセッション*/
    private final HttpSession session;
    /**セッションキー(ログインユーザのアカウント)*/
    private static final String SESSION_FORM_ID = "account";

    /** セッションキー(検索フォーム) */
    private static final String SESSION_SEARCH_FORM_ID ="historyForm";

    /**  ページングサイズ */
    private static final int DEFAULT_PAGEABLE_SIZE = 10;


    /**
     * コード値
     */
    private final CodeValue codeValue;

    @Autowired
    public TopController(TopService topService, HistoryService historyService, HttpSession session, CodeValue codeValue) {
        this.service = topService;
        this.historyService = historyService;
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

     * @return Path
     */
    @RequestMapping(value = "")
    public String init(@AuthenticationPrincipal Account account) {
        // 初回のアクセスなら、アカウントを検索してセッションに格納する
        if (Objects.isNull(session.getAttribute(SESSION_FORM_ID))) {
            Account sessionAccount = service.getAccountById(account.getAccountId());
            session.setAttribute(SESSION_FORM_ID, sessionAccount);
        }
        return "top/topForm";
    }

    /**
     * アカウント検索-検索結果表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/do")
    public String search(@ModelAttribute @Validated HistoryForm historyForm, BindingResult bindingResult, Model model) {

        // 検索条件からアカウントを検索する
        List<Account> list = historyService.findHistory(historyForm);
        if (Objects.isNull(list) || list.isEmpty()) {
            // 検索結果が０件ならエラー表示
            bindingResult.reject("validation.noSearchResult", "default message");
            return "top/topForm";
        }
        // 検索結果を画面に表示する
        model.addAttribute("list", list);
        return "top/topForm";
    }


    /**
     * アカウント検索（ページング）-初期表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/paging/init")
    public String pagingSearchInit(@ModelAttribute HistoryForm historyForm) {
        // セッション内に残っている検索条件を削除
        session.getAttribute(SESSION_SEARCH_FORM_ID);
        return "top/history";
    }

    /**
     * アカウント検索（ページング）-検索結果表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/paging/do")
    public String pagingSearch(@ModelAttribute @Validated HistoryForm historyForm, BindingResult bindingResult,
                               @PageableDefault(size = DEFAULT_PAGEABLE_SIZE)
                               @SortDefault.SortDefaults(
                                       {@SortDefault(
                                               sort = "accountId",
                                               direction = Sort.Direction.ASC
                                       )}) Pageable pageable,
                               Model model) {

        // 検索条件を持ち回るためにセッションに格納し、画面表示する。
        session.setAttribute(SESSION_SEARCH_FORM_ID, historyForm);
        model.addAttribute(historyForm);
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "top/topHistory";
        }

        // 検索条件からアカウントを検索する
        Page<Account> page = historyService.findHistory(historyForm, pageable);
        List<Account> list = page.getContent();
        if (Objects.isNull(list) || list.isEmpty()) {
            // 検索結果が０件ならエラー表示
            bindingResult.reject("validation.noSearchResult", "default message");
            return "top/history";
        }
        // 検索結果、ページング情報を格納する
        model.addAttribute("page", page);
        model.addAttribute("list", list);
        model.addAttribute("url", "/account/find");
        return "top/history";
    }

    /**
     * ページング。
     *
     * @param historyForm 検索条件
     * @param bindingResult 精査結果
     * @param pageable ページャブル
     * @param model モデル
     * @return path
     */
    @RequestMapping(value = "/paging/pagenation")
    public String pagination(@ModelAttribute @Validated HistoryForm historyForm, BindingResult bindingResult,
                             @PageableDefault(size = DEFAULT_PAGEABLE_SIZE)
                             @SortDefault.SortDefaults(
                                     {@SortDefault(
                                             sort = "accountId",
                                             direction = Sort.Direction.ASC
                                     )}) Pageable pageable,
                             Model model) {
        // セッションに格納されている検索条件からページング検索
        HistoryForm storedCondition = (HistoryForm) session.getAttribute(SESSION_SEARCH_FORM_ID);
        return this.pagingSearch(storedCondition, bindingResult, pageable, model);
    }



}
