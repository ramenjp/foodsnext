package com.dev_training.service27;


import com.dev_training.entity27.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント情報更新サービス
 */
@Service
public class AccountUpdateService {

    /**  アカウントレポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public AccountUpdateService(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    /**
     * アカウント最新データ取得処理
     * 必要であれば
     *
     */

    /**
     * 更新処理
     *
     * @param account 更新対象のアカウント
     */
    @Transactional
    public void updateAccount(Account account){accountRepository.save(account);}

}
