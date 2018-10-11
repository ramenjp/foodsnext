package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.TodoRegisterForm;
import com.dev_training.service.TodoRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * TODO登録コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/register")
public class TodoRegisterController {

    /** TODO登録サービス */
    private final TodoRegisterService service;
    private final AccountRepository accountRepository;
    private final CodeValue codeValue;

    @Autowired
    public TodoRegisterController(TodoRegisterService todoRegisterService, AccountRepository accountRepository, CodeValue codeValue) {
        this.service = todoRegisterService;
        this.accountRepository = accountRepository;
        this.codeValue = codeValue;
    }

    /**
     * TODO登録-初期表示。
     *
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/init")
    String registerInit(@ModelAttribute("todoRegisterForm") TodoRegisterForm todoRegisterForm, Model model) {
        // フォームの初期化
        model.addAttribute("todoRegisterForm", todoRegisterForm);
        // 担当者選択用のプルダウンリスト
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accountList", accounts);
        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        return "todo/todoRegisterForm";
    }

    /**
     * TODO登録-確認画面表示。
     *
     * @param todoRegisterForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @param model            モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm")
    String registerConfirm(@Validated TodoRegisterForm todoRegisterForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "todo/todoRegisterForm";
        }

        if (service.isValidDate(todoRegisterForm.getStartDate(), todoRegisterForm.getEndDate())) {
            bindingResult.reject("validation.invalidDate", "default message");
            return "todo/todoRegisterForm";
        }

        model.addAttribute("todoRegisterForm", todoRegisterForm);
        return "todo/todoRegisterConfirmForm";
    }

    /**
     * TODO登録-完了画面表示。
     *
     * @param todoRegisterForm 精査済みフォーム
     * @param bindingResult    精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "register", method = RequestMethod.POST)
    String registerComplete(@Validated TodoRegisterForm todoRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todo/todoRegisterForm";
        }
//        Account account = new Account();
//        account.setAccountId(accountRegisterForm.getAccountId());
//        account.setName(accountRegisterForm.getName());
//        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
//        account.setEmail(accountRegisterForm.getEmail());
//        service.register(account, accountRegisterForm.getPassword());
        return "todo/todoRegisterCompleteForm";
    }

    /**
     * TODO登録-入力画面に戻る。
     *
     * @param todoRegisterForm TODO登録フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "registerBack", method = RequestMethod.POST)
    String registerBack(TodoRegisterForm todoRegisterForm, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("todoRegisterForm", todoRegisterForm);
        return "redirect:/todo/register/init";
    }

}
