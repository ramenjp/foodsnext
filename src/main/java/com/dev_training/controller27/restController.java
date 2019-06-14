package com.dev_training.controller27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.entity27.Matching;
import com.dev_training.entity27.MatchingRepository;
import com.dev_training.service27.AccountMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//マッチngテーブルから２人ずつのペアを作る
@RestController
@RequestMapping(value="/top/matching2")
public class restController {
    public final AccountMatchingService accountMatchingService;

    @Autowired
    public restController(AccountMatchingService accountMatchingService) {
        this.accountMatchingService = accountMatchingService;
    }

    @RequestMapping("")
    public void batch(){

        Date date=new Date();
        Calendar cl = Calendar.getInstance();

        //例外処理
        ParsePosition pos = new ParsePosition(0);

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        //StringをDate型に
        String formattedDate = df.format(cl.getTime());

        Date nowDate = df.parse(formattedDate,pos);



        //今日のマッチアカウントを全件取得
        List<Matching> matchingAccounts= accountMatchingService.getMatchingAccounts(nowDate.toString());

        //matchingNo付与して再度DB格納
        //リスト長分for回す→マッチングNoつけていく

        for(int i=0;i<matchingAccounts.size();i++){
            //matchingNo付与
            accountMatchingService.setMatchingNo(i);
            //再度matchingテーブルに保存
            accountMatchingService.register(matchingAccounts.get(i));
        }
    }
}