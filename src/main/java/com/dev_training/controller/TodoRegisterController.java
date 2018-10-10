package com.dev_training.controller;

import com.dev_training.common.CodeValue;
import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.AccountRegisterForm;
import com.dev_training.form.TodoRegisterForm;
import com.dev_training.service.TodoRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    String registerInit(Model model) {
        // フォームの初期化
        model.addAttribute("todoRegisterForm", new TodoRegisterForm());
        // 担当者選択用のプルダウンリスト
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        // ステータスプルダウンの初期化
        model.addAttribute("allStatus", codeValue.getStatus());
        // 優先度プルダウンの初期化
        model.addAttribute("allPriority", codeValue.getPriority());

        return "todo/todoRegisterForm";
    }

    /**
     * TODO登録-確認画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @param model               モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm")
    String registerConfirm(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "todo/todoRegisterForm";
        }
        if (service.isExistsAccountId(accountRegisterForm.getAccountId())) {
            bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
            return "todo/todoRegisterForm";
        }
        model.addAttribute("accountRegisterForm", accountRegisterForm);
        return "todo/todoRegisterConfirmForm";
    }

    /**
     * TODO登録-完了画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @return Path
     */
    @RequestMapping(value = "/do", params = "register", method = RequestMethod.POST)
    String registerComplete(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todo/todoRegisterForm";
        }
        Account account = new Account();
        account.setAccountId(accountRegisterForm.getAccountId());
        account.setName(accountRegisterForm.getName());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
        account.setEmail(accountRegisterForm.getEmail());
        service.register(account, accountRegisterForm.getPassword());
        return "todo/todoRegisterCompleteForm";
    }

    /**
     * TODO登録-入力画面に戻る。
     *
     * @param accountRegisterForm アカウント登録フォーム。
     * @return Path
     */
    @RequestMapping(value = "/do", params = "registerBack", method = RequestMethod.POST)
    String registerBack(AccountRegisterForm accountRegisterForm) {
        return "todo/todoRegisterForm";
    }

}
