package com.dev_training.controller27;

import com.dev_training.entity27.Account;
import com.dev_training.service27.AccountMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
<<<<<<< HEAD
=======
import java.util.Calendar;
>>>>>>> 53fa1b60757bac81522875f61ce6bb285f79f526
import java.util.Date;

/***
 マッチ用コントローラ。
*/

@Controller
<<<<<<< HEAD
@RequestMapping(value = "/matching2")
=======
@RequestMapping(value = "/matching3")
>>>>>>> 53fa1b60757bac81522875f61ce6bb285f79f526
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
<<<<<<< HEAD
        ParsePosition pos = new ParsePosition(0);
        String currentDate = date.toString();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        Date formattedDate = df.parse(currentDate,pos);
=======
        Calendar cl = Calendar.getInstance();

        //例外処理
        ParsePosition pos = new ParsePosition(0);

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        String formattedDate = df.format(cl.getTime());

        Date nowDate = df.parse(formattedDate,pos);

>>>>>>> 53fa1b60757bac81522875f61ce6bb285f79f526

        //自分のアカウントIDを取得
        Account account = (Account)session.getAttribute(SESSION_FORM_ID);
        int accountId = account.getAccountId();
        //取得した自分のアカウントIDを使ってmatchingNoを取得
<<<<<<< HEAD
        int myMatchingNo = accountMatchingService.getMatchingNo(accountId,formattedDate);
=======
        int myMatchingNo = accountMatchingService.getMatchingNo(accountId,nowDate);
>>>>>>> 53fa1b60757bac81522875f61ce6bb285f79f526

        //matchingNoが同じアカウントのアカウントIDを検索,取得
        int matchingPartnerId = accountMatchingService.getMatchingPartnerId(accountId,myMatchingNo);

        //パートナーのアカウントIdを使ってaccountsテーブルにアカウント情報を取得しにいく
        Account partner = accountMatchingService.findPartnerAccount(matchingPartnerId);
        //自分のアカウントIdを使ってaccountsテーブルにアカウント情報を取得しにいく

        model.addAttribute("account",account);
        model.addAttribute("partner",partner);

        return "matching/testForm";
    }
}