package com.dev_training.controller27;


import com.dev_training.entity27.Account;
import com.dev_training.form27.AccountRegisterForm;
import com.dev_training.service27.AccountRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * アカウント登録コントローラ。
 */

@Controller
@RequestMapping(value = "/account/register")
public class AccountRegisterController {
    /** アカウント登録サービス */
    private final AccountRegisterService service;

    @Autowired
    public AccountRegisterController(AccountRegisterService accountRegisterService) {
        this.service = accountRegisterService;
    }



    /**
     * アカウント登録-初期表示。
     *
     * @param accountRegisterForm アカウント登録フォーム
     * @return Path
     */
    @RequestMapping(value = "/init")
    public String registerInit(@ModelAttribute AccountRegisterForm accountRegisterForm) {
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
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String registerConfirm(@ModelAttribute @Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult, Model model) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        // アカウントIDの重複精査

        if (service.isExistsAccountId(accountRegisterForm.getEmail())) {
            bindingResult.rejectValue("email", "validation.duplicate", new String[]{"メール"}, "default message");
            return "account/accountRegisterForm";
        }
        return "account/accountRegisterConfirmForm";
    }


    /**
     * アカウント登録-完了画面表示。
     *
     * @param accountRegisterForm 精査済みフォーム
     * @param bindingResult       精査結果
     * @return Path
     */

    /*doからcompleteに編集*/
    @RequestMapping(value = "/complete", params = "register", method = RequestMethod.POST)
    public String registerComplete(@ModelAttribute @Validated AccountRegisterForm accountRegisterForm, BindingResult bindingResult) {
        // BeanValidationのエラー確認
        if (bindingResult.hasErrors()) {
            return "account/accountRegisterForm";
        }
        // 登録するアカウントの作成
        Account account = new Account();

        account.setNickname(accountRegisterForm.getNickname());
        account.setEmail(accountRegisterForm.getEmail());
        account.setDepartmentPosition(accountRegisterForm.getDepartment_position());
        account.setSelfIntroduction(accountRegisterForm.getSelfIntroduction());


        // アカウントの登録
        service.register(account,accountRegisterForm.getPassword());
        return "account/accountRegisterCompleteForm";
    }

    /**
     * アカウント登録-入力画面に戻る。
     *
     * @param accountRegisterForm アカウント登録フォーム。
     * @return Path
     */
    @RequestMapping(value = "/complete", params = "registerBack", method = RequestMethod.POST)
    public String registerBack(@ModelAttribute AccountRegisterForm accountRegisterForm) {

        return "account/accountRegisterForm";
    }

}