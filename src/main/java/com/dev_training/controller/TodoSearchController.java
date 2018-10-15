package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Todo;
import com.dev_training.form.TodoSearchForm;
import com.dev_training.service.TodoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public TodoSearchController(TodoSearchService todoSearchService, AccountRepository accountRepository, CodeValue codeValue) {
        this.service = todoSearchService;
        this.accountRepository = accountRepository;
        this.codeValue = codeValue;
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
     * リダイレクト処理。
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
