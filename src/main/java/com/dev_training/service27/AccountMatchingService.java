package com.dev_training.service27;

import com.dev_training.entity27.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AccountMatchingService {

    /** マッチングレポジトリ　*/
    private final AccountRepository accountRepository;
    private final MatchingRepository matchingRepository;

    private final HistoryRepository historyRepository;



    @Autowired
    public AccountMatchingService(AccountRepository accountRepository,MatchingRepository matchingRepository, HistoryRepository historyRepository){
        this.accountRepository = accountRepository;
        this.matchingRepository = matchingRepository;
        this.historyRepository =historyRepository;


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
    @Transactional
    public void setMatchingNo(int matchingNo){
        if(matchingNo % 2==0) {
            matchingNo+=1;
            matchingRepository.insertMatchingNo(matchingNo);
        }
        else{
            matchingRepository.insertMatchingNo(matchingNo);
        }
    }

    /*     ここからAccountMatchingResultControllerが使うメソッド     */
    @Transactional
    public int getMatchingNo(int accountId, Date formattedDate) {

        Matching myAccount =matchingRepository.findMyMatchingNo(accountId,formattedDate);
        int myMatchingNo = myAccount.getMatchingNo();
        return myMatchingNo;
    }

    @Transactional
    public int getMatchingPartnerId(int accountId,int matchingNo) {
        Matching partner = matchingRepository.findMyMatchingPartnerNo(accountId,matchingNo);
        int matchingPartnerId=partner.getAccountId();
        return matchingPartnerId;
    }

    @Transactional
    public Account findPartnerAccount(int matchingPartnerId) {
        Account partnerAccount = accountRepository.findByAccountId(matchingPartnerId);
        return partnerAccount;
    }


    //DBにHistoryアカウントを登録する処理
    @Transactional
    public void registerHistory(History historyAccount){
        historyRepository.save(historyAccount);
    }


}