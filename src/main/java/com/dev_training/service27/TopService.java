package com.dev_training.service27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
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
     * @param id アカウントの主キー
     * @return アカウント
     */
    public Account getAccountById(int id) {
        // 取れないことは考慮しない。
        return accountRepository.findById(id).get();
    }
}