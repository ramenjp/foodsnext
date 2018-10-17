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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    /** フォーム名 */
    private static final String FORM_NAME = "todoSearchForm";
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
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String searchInit(@ModelAttribute("todoSearchForm") TodoSearchForm todoSearchForm, Model model) {
        // フォームの初期化
        model.addAttribute("todoSearchForm", todoSearchForm);
        // 担当者選択用のプルダウンリスト
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

        // リダイレクト元で設定されているエラーをモデルに格納して、画面に表示。
        String key = BindingResult.MODEL_KEY_PREFIX + FORM_NAME;
        if (model.asMap().containsKey("errors")) {
            model.addAttribute(key, model.asMap().get("errors"));
        }

        // リダイレクト元で検索結果が存在する場合、画面に表示。
        if (model.asMap().containsKey("list")) {
            model.addAttribute("list", model.asMap().get("list"));
        }

        return "todo/todoSearchForm";
    }

    /**
     * TODO検索-検索結果表示。
     *
     * @return Path
     */
    @RequestMapping(value = "/do")
    public String search(@Validated TodoSearchForm todoSearchForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectToInit(todoSearchForm, bindingResult, redirectAttributes);
        }
        List<Todo> list = service.findTodo(todoSearchForm);
        if (list == null || list.isEmpty()) {
            bindingResult.reject("validation.noSearchResult", "default message");
            redirectToInit(todoSearchForm, bindingResult, redirectAttributes);
        }
        redirectAttributes.addFlashAttribute("list", list);
        return redirectToInit(todoSearchForm, bindingResult, redirectAttributes);
    }

    /**
     * TODO検索-詳細表示。
     *
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
        if (result == null) {
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
     * リダイレクト処理(TODO削除)。
     *
     * @param todoId 削除対象のTODOのID
     * @param model モデル
     * @param redirectAttributes redirectAttributes
     * @return リダイレクトURL
     */
    @RequestMapping(value = "/update", params = "delete")
    public String redirect(@RequestParam String todoId, Model model, RedirectAttributes redirectAttributes) {
        return "forward:/todo/delete/confirm";
    }

    /**
     * リダイレクト処理(検索)。
     *
     * @param todoSearchForm フォーム
     * @param bindingResult 精査結果
     * @param redirectAttributes redirectAttributes
     * @return リダイレクトURL
     */
    private String redirectToInit(@Validated TodoSearchForm todoSearchForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("todoSearchForm", todoSearchForm);
        redirectAttributes.addFlashAttribute("errors", bindingResult);
        return "redirect:/todo/search/init";
    }
}
