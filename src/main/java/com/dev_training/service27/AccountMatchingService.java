package com.dev_training.service27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.entity27.Matching;
import com.dev_training.entity27.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AccountMatchingService {

    /** マッチングレポジトリ　*/
    private final AccountRepository accountRepository;
    private final MatchingRepository matchingRepository;

    @Autowired
    public AccountMatchingService(AccountRepository accountRepository,MatchingRepository matchingRepository){
        this.accountRepository = accountRepository;
        this.matchingRepository = matchingRepository;
    }

    //アカウント
    /**
     * @Param targetId 自分のaccountId
     * */
    @Transactional
    public int getAccountIdByEmail(String email){
        Account accountIdByEmail= accountRepository.findAccountIdByEmail(email);
        int targetId =accountIdByEmail.getAccountId();
        return targetId;
    }

    //DBにマッチングアカウントを登録する処理
    @Transactional
    public void register(Matching matchingAccount){
        matchingRepository.save(matchingAccount);
    }


    //自分のmatchingテーブル内のアカウントを取得
    //自分と同じグループNo.のアカウントを取り出す
    //マッチした相手のアカウント情報をから取得
    @Transactional
    public List<Matching> getMatchingAccounts(String nowDate){
        //ランダム数を昇順に並び替え
        List<Matching> matchingAccounts = matchingRepository.findTodayAccount(nowDate);
        return matchingAccounts;
    }

    //マッチングナンバーを割り当てる処理
    public int createMatchingNo(int i) {
        if (i % 2 == 0) {
            return i - 1;
        } else {
            return i;
        }
    }

        @Transactional
        public void updateMatchingNo(Matching matching){
           matchingRepository.save(matching);
        }


    /*     ここからAccountMatchingResultControllerが使うメソッド     */
    @Transactional
    public Matching getMatchingAccount(int accountId, String formattedDate) {

        Matching myAccount =matchingRepository.findMyMatchingNo(accountId,formattedDate);
//        if (Objects.isNull(myAccount)) {
//        int myMatchingNo = myAccount.getMatchingNo();
//        return myMatchingNo;
        return myAccount;
    }

    @Transactional
    public Matching getMatchingNo(int accountId, String formattedDate) {
        Matching myAccount =matchingRepository.findMyMatchingNo(accountId,formattedDate);
        return myAccount;
    }

    @Transactional
        public int getMatchingPartnerId(int accountId,int matchingNo,String formattedDate) {
        Matching partner = matchingRepository.findMyMatchingPartnerNo(accountId,matchingNo,formattedDate);
        int matchingPartnerId=partner.getAccountId();
        return matchingPartnerId;
    }

    @Transactional
    public Account findPartnerAccount(int matchingPartnerId) {
        Account partnerAccount = accountRepository.findByAccountId(matchingPartnerId);
        return partnerAccount;
    }
}