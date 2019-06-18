package com.dev_training.controller27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.Matching;
import com.dev_training.service27.AccountMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * マッチ用コントローラ。
 */



@Controller
@RequestMapping(value = "/top/matching/judge")
public class AccountMachingJudgeController {

    public final AccountMatchingService accountMatchingService;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountMachingJudgeController(AccountMatchingService accountMatchingService ,HttpSession session) {
        this.accountMatchingService = accountMatchingService;
            this.session = session;
    }

    @RequestMapping("")
    public String judge() {

        Date date = new Date();
        Calendar cl = Calendar.getInstance();

        //例外処理
        ParsePosition pos = new ParsePosition(0);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        String formattedDate = df.format(cl.getTime());

        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        int accountId = account.getAccountId();


        Matching myMatching = accountMatchingService.getMatchingNo(accountId, formattedDate);
        if (Objects.isNull(myMatching)) {

            return "top/topForm";
        } else {
            if (myMatching.getMatchingNo() == 0) {
                return "top/topForm";
            } else {
                return "matching/matchingResultForm";
            }

        }

    }
}
