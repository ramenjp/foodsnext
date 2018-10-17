package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Todo;
import com.dev_training.form.TodoSearchForm;
import com.dev_training.service.TodoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * TODO検索コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/search")
public class TodoSearchController {

    /** TODO検索サービス */
    private final TodoSearchService service;
    /** アカウントサービス */
    private final AccountRepository accountRepository;
    /** コード値 */
    private final CodeValue codeValue;
    /** メッセージソース */
    private final MessageSource messageSource;

    @Autowired
    public TodoSearchController(TodoSearchService todoSearchService, AccountRepository accountRepository, CodeValue codeValue, MessageSource messageSource) {
        this.service = todoSearchService;
        this.accountRepository = accountRepository;
        this.codeValue = codeValue;
        this.messageSource = messageSource;
    }

    /**
     * TODO検索-初期表示。
     *
     * @param todoSearchForm 検索フォーム
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String searchInit(@ModelAttribute("todoSearchForm") TodoSearchForm todoSearchForm, Model model) {
        // 担当者選択用のプルダウンリストの初期化
        List<Account> accounts = accountRepository.findAll();
        Map<Integer, String> accountMap = new HashMap<>();
        for (Account account :accounts) {
            accountMap.put(account.getId(), account.getName());
        }
        model.addAttribute("accountList", accountMap);

        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        return "todo/todoSearchForm";
    }

    /**
     * TODO検索-検索結果表示。
     *
     * @param todoSearchForm 検索フォーム
     * @param bindingResult 精査結果
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/do")
    public String search(@Validated TodoSearchForm todoSearchForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // forwardさせるとエラー情報が消えるので、メソッド呼び出しで処理する。
            // TodoRegisterと同様に、RedirectAttributesに情報を詰めてリダイレクトし、先で取り出してModel.addattributeさせるのでもOK。
            return this.searchInit(todoSearchForm, model);
        }

        // 検索処理
        List<Todo> list = service.findTodo(todoSearchForm);
        if (Objects.isNull(list) || list.isEmpty()) {
            bindingResult.reject("validation.noSearchResult", "default message");
            return this.searchInit(todoSearchForm, model);
        }

        // 検索結果の格納
        model.addAttribute("list", list);
        return "forward:/todo/search/init";
    }

    /**
     * TODO検索-詳細表示。
     *
     * @param todoId 対象のTODOのID
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/detail")
    public String detail(@RequestParam(defaultValue = "") String todoId, Model model) {
        // 元画面からidが渡ってこなければ、エラー表示。
        if (StringUtils.isEmpty(todoId)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.invalid.screen,transition", null, Locale.JAPAN));
            return "common/commonError";
        }

        // idに紐づくTODOが取得できなければ、エラー表示。
        Todo result = service.findById(Integer.parseInt(todoId));
        if (Objects.isNull(result)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.incorrect.specification.todo", null, Locale.JAPAN));
            return "common/commonError";
        }

        // TODOに紐づくアカウントを取得する。
        Optional<Account> issuePersonAccount = accountRepository.findById(result.getIssuePersonId());
        Optional<Account> inChargeAccount = accountRepository.findById(result.getPersonInChargeId());
        // 画面に表示する起票者名、担当者名をセットする。存在しないユーザとなっている場合、退会済ユーザと表示する。
        if (issuePersonAccount.isPresent()) {
            model.addAttribute("issuePersonName", issuePersonAccount.get().getName());
        } else {
            model.addAttribute("issuePersonName", "退会済ユーザ");
        }
        if (inChargeAccount.isPresent()) {
            model.addAttribute("personInChargeName", inChargeAccount.get().getName());
        } else {
            model.addAttribute("personInChargeName", "退会済ユーザ");
        }

        // 詳細画面に表示するステータス、優先度をセットする。
        model.addAttribute("statusName", codeValue.getStatus().getStatus().get(result.getStatus()));
        model.addAttribute("priorityName", codeValue.getPriority().getPriority().get(result.getPriority()));
        model.addAttribute("todo", result);
        return "todo/todoSearchDetailForm";
    }

    /**
     * フォワード。(TODO削除)。
     *
     * @param todoId 削除対象のTODOのID
     * @param model モデル
     * @return フォワードURL
     */
    @RequestMapping(value = "/update", params = "delete")
    public String forward(@RequestParam String todoId, Model model) {
        return "forward:/todo/delete/confirm";
    }
}
