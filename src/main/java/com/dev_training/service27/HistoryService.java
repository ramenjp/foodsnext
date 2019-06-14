package com.dev_training.service27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.form27.HistoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {

    /**
     * アカウントリポジトリ
     */
    private final AccountRepository accountRepository;

    @Autowired
    public HistoryService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /**
     * 検索条件からアカウントを検索する。
     *
     * @param form 検索条件
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public List<Account> findHistory(HistoryForm form) {
        return accountRepository.findAll();
    }


    /**
     * 検索条件からアカウントを検索する（ページング）。
     *
     * @param form     検索条件
     * @param pageable ページャブル
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public Page<Account> findHistory(HistoryForm form, Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

}

