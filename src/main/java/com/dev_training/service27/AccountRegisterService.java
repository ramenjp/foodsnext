package com.dev_training.service27;

import com.dev_training.entity27.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント登録サービス
 */
@Service
public class AccountRegisterService {

    /** アカウントレポジトリ　*/
    private final AccountRepository accountRepository;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    /**
     * 登録処理
     *
     * @param form フォーム
     */

    @Transactional
    public void register(AccountResisterForm form){
        Account account = new Account();
        accountRepository.save(account);
    }
    /**
     * アカウントID重複精査
     * @param accountId
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String accountId){
        int result = accountRepository.countByAccountId(accountId);
        return result !=0;
    }

}
