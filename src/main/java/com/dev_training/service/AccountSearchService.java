package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.AccountSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev_training.service.AccountSpecifications.*;


/**
 * アカウント検索サービス。
 */
@Service
public class AccountSearchService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountSearchService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * 検索。
     *
     * @param form 検索条件
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public List<Account> findAccount(AccountSearchForm form) {
        return accountRepository.findAll(
                Specification
                        .where(accountIdContains(form.getAccountId()))
                        .and(nameContains(form.getName()))
                        .and(emailContains(form.getEmail())));
    }

    /**
     * 検索（ページング）。
     *
     * @param form     検索条件
     * @param pageable ページャブル
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public Page<Account> findAccount(AccountSearchForm form, Pageable pageable) {
        return accountRepository.findAll(
                Specification
                        .where(accountIdContains(form.getAccountId()))
                        .and(nameContains(form.getName()))
                        .and(emailContains(form.getEmail()))
                , pageable);
    }
}
