package com.dev_training.controller27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.entity27.Matching;
import com.dev_training.entity27.MatchingRepository;
import com.dev_training.service27.AccountMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpSession;

/**
 * マッチ用コントローラ。
 */

@Controller
@RequestMapping(value = "/matching1")
public class AccountMatchingController {

    /** アカウントマッチングサービス */
    public final AccountMatchingService accountMatchingService;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountMatchingController(AccountMatchingService accountMatchingService,HttpSession session) {
        this.accountMatchingService = accountMatchingService;
        this.session = session;
    }

    //マッチングテーブルにアカウントを登録
    @RequestMapping(value = "")
    public String matchingAccountRegister(Model model){

        //accountsテーブルからaccount_idを取得
        Account account = (Account)session.getAttribute(SESSION_FORM_ID);
        int accountId = account.getAccountId();

        //ランダム関数
        Random rnd = new Random();
        int RandomValue = rnd.nextInt();
        //日付関数
        Date date=new Date();
        //例外処理
        ParsePosition pos = new ParsePosition(0);

        String currentDate = date.toString();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        Date formattedDate = df.parse(currentDate,pos);

        //マッチング用のアカウントインスタンス生成
        Matching matchingAccount = new Matching();

        matchingAccount.setAccountId(accountId);
        matchingAccount.setShuffleNo(RandomValue);
        matchingAccount.setMatchingDate(formattedDate);

        accountMatchingService.register(matchingAccount);
        return "matching_count";
    }
}