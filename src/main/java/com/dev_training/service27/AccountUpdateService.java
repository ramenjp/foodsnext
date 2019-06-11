package com.dev_training.service27;


import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.util.Optional;

/**
 * アカウント情報更新サービス
 */
@Service
public class AccountUpdateService {

    /**
     * アカウントレポジトリ
     */
    private final AccountRepository accountRepository;

    @Autowired
    public AccountUpdateService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * アカウントIDの重複精査
     */

    @Transactional(readOnly = true)
    public boolean isExistsEmail(String email) {
        // アカウントIDの重複精査
        int result = accountRepository.countByAccount(accountId);
        return result != 0;
    }
    /**
     * 更新処理
     *
     * @param account 更新対象のアカウント
     */

    /**
     * 更新処理。
     *
     * @param account 更新対象のアカウント
     */
    @Transactional
    public void updateAccountById(Account account) {
        accountRepository.save(account);
    }
}

