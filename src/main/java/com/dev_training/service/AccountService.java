package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.AccountSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev_training.service.AccountSpecifications.*;


/**
 * アカウントサービス。
 */
@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     */
    public void create(Account account, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    /**
     * アカウントIDの重複精査。
     *
     * @param accountId 精査対象のアカウントID
     * @return true:未存在 false:存在
     */
    public boolean isExistsAccountId(String accountId) {
        // アカウントIDの重複精査
        int result = accountRepository.countByAccountId(accountId);
        return result != 0;
    }

    /**
     * 検索。
     *
     * @param form     検索条件
     * @param pageable ページャブル
     * @return 検索結果
     */
    public Page<Account> findAccount(AccountSearchForm form, Pageable pageable) {
        return accountRepository.findAll(
                Specification
                        .where(accountIdContains(form.getAccountId()))
                        .and(nameContains(form.getName()))
                        .and(emailContains(form.getEmail()))
                , pageable);
    }

    /**
     * 検索。
     *
     * @param form 検索条件
     * @return 検索結果
     */
    public List<Account> findAccount(AccountSearchForm form) {
        return accountRepository.findAll(
                Specification
                        .where(accountIdContains(form.getAccountId()))
                        .and(nameContains(form.getName()))
                        .and(emailContains(form.getEmail())));
    }
}
