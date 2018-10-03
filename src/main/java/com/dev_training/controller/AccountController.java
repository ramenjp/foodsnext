package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.AccountRegisterForm;
import com.dev_training.form.AccountSearchForm;
import com.dev_training.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * アカウントコントローラ。
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    /**
     * アカウント登録サービス
     */
    private final AccountService accountService;

    /**
     * ページングサイズ。
     */
    private static final int DEFAULT_PAGEABLE_SIZE = 10;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * アカウント登録-初期表示。
     *
     * @param accountRegisterForm AttributeForm
     * @return Path
     */
    @RequestMapping(value = "/register/init")
    String registerInit(@ModelAttribute AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }

    /**
     * アカウント登録-確認画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @param model               モデル
     * @return Path
     */
    @RequestMapping(value = "/register/confirm")
    String registerConfirm(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        if (accountService.isExistsAccountId(accountRegisterForm.getAccountId())) {
            bindingResult.rejectValue("accountId", "validation.duplicate", new String[]{"アカウントID"}, "default message");
            return "account/accountRegisterForm";
        }
        model.addAttribute("accountRegisterForm", accountRegisterForm);
        return "account/accountRegisterConfirmForm";
    }

    /**
     * アカウント登録-完了画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @return Path
     */
    @RequestMapping(value = "/register/do", params = "register", method = RequestMethod.POST)
    String registerComplete(@Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        Account account = new Account();
        account.setAccountId(accountRegisterForm.getAccountId());
        account.setName(accountRegisterForm.getName());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());
        account.setEmail(accountRegisterForm.getEmail());
        accountService.create(account, accountRegisterForm.getPassword());
        return "account/accountRegisterCompleteForm";
    }

    /**
     * アカウント登録-入力画面に戻る。
     *
     * @param accountRegisterForm フォーム。
     * @return Path
     */
    @RequestMapping(value = "/register/do", params = "registerBack", method = RequestMethod.POST)
    String registerBack(AccountRegisterForm accountRegisterForm) {
        return "account/accountRegisterForm";
    }

    /**
     * アカウント検索-初期表示
     *
     * @return Path
     */
    @RequestMapping(value = "/search/init")
    public String searchInit(@ModelAttribute AccountSearchForm accountSearchForm) {
        return "account/accountSearchForm";
    }

    /**
     * アカウント検索-検索結果表示
     *
     * @return Path
     */
    @RequestMapping(value = "/search/do")
    public String search(@Validated AccountSearchForm accountSearchForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "account/accountSearchForm";
        }

        List<Account> list = accountService.findAccount(accountSearchForm);
        if (list.isEmpty()){
            bindingResult.reject("validation.noSearchResult", "default message");
            return "account/accountSearchForm";
        }
        model.addAttribute("list", list);
        return "account/accountSearchForm";
    }


    /**
     * アカウント検索（ページング）-初期表示
     *
     * @return Path
     */
    @RequestMapping(value = "/search/paging/init")
    public String pagingSearchInit(@ModelAttribute AccountSearchForm accountSearchForm) {
        return "account/accountPagingSearchForm";
    }

    /**
     * アカウント検索（ページング）-検索結果表示
     *
     * @return Path
     */
    @RequestMapping(value = "/search/paging/do")
    public String pagingSearch(@Validated AccountSearchForm accountSearchForm, BindingResult bindingResult,
                       @PageableDefault(size = DEFAULT_PAGEABLE_SIZE)
                       @SortDefault.SortDefaults(
                               {@SortDefault(
                                       sort = "accountId",
                                       direction = Sort.Direction.ASC
                               )}) Pageable pageable,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "account/accountPagingSearchForm";
        }

        Page<Account> page = accountService.findAccount(accountSearchForm, pageable);
        List<Account> list = page.getContent();
        if (list.isEmpty()) {
            bindingResult.reject("validation.noSearchResult", "default message");
            return "account/accountPagingSearchForm";
        }
        model.addAttribute("page", page);
        model.addAttribute("list", list);
        model.addAttribute("url", "/account/find");
        return "account/accountPagingSearchForm";
    }
}
