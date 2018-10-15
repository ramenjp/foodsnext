package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * トップサービス。
 */
@Service
@Transactional
public class TopService {

    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public TopService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * アカウント最新データ取得処理。
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    public Account getAccountByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId);
    }
}