package com.dev_training.controller27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.History;
import com.dev_training.service27.AccountMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 マッチ用コントローラ。
*/

@Controller
@RequestMapping(value = "/matching3")
public class AccountMatchingResultController {

    /**
     * アカウントマッチングサービス
     */
    public final AccountMatchingService accountMatchingService;
    /**
     * HTTPセッション
     */
    private final HttpSession session;
    /**
     * セッションキー(ログインユーザのアカウント)
     */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountMatchingResultController(AccountMatchingService accountMatchingService, HttpSession session) {
        this.accountMatchingService = accountMatchingService;
        this.session = session;
    }

    //グループ分けされた結果をmatchingDBに見に行く処理
    @RequestMapping(value = "")
    public String matchingAccountRegister(Model model) {
        Date date=new Date();
        Calendar cl = Calendar.getInstance();

        //例外処理
        ParsePosition pos = new ParsePosition(0);

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        String formattedDate = df.format(cl.getTime());

        Date nowDate = df.parse(formattedDate,pos);


        //自分のアカウントIDを取得
        Account account = (Account)session.getAttribute(SESSION_FORM_ID);
        int accountId = account.getAccountId();
        //取得した自分のアカウントIDを使ってmatchingNoを取得
        int myMatchingNo = accountMatchingService.getMatchingNo(accountId,nowDate);

        //matchingNoが同じアカウントのアカウントIDを検索,取得
        int matchingPartnerId = accountMatchingService.getMatchingPartnerId(accountId,myMatchingNo);

        //パートナーのアカウントIdを使ってaccountsテーブルにアカウント情報を取得しにいく
        Account partner = accountMatchingService.findPartnerAccount(matchingPartnerId);
        //自分のアカウントIdを使ってaccountsテーブルにアカウント情報を取得しにいく

        model.addAttribute("account",account);
        model.addAttribute("partner",partner);




        //History用のアカウントインスタンス生成
        History historyAccount =new History();
        historyAccount.setAccountId(account.getAccountId());
        historyAccount.setHistoryPartnerId(partner.getAccountId());
        accountMatchingService.registerHistory(historyAccount);


        return "matching/testForm";
    }
}