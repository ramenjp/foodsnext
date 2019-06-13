package com.dev_training.service27;


import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.form27.AccountUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * アカウント情報更新サービス
 */
@Service
public class AccountUpdateService {

    /** アカウントレポジトリ*/
    private final AccountRepository accountRepository;

    @Autowired
    public AccountUpdateService(AccountRepository accountRepository, PasswordEncoder passwordEncoder)
    {
        this.accountRepository = accountRepository;
    }

    /**
     * アカウント最新データ取得処理。
     *
     * @param email Email
     * @return アカウントエンティティ
     */
    @Transactional(readOnly = true)
    public Account getAccountByEmail(String email) {
        Optional<Account> result = Optional.ofNullable(accountRepository.findByEmail(email));
        return result.orElseThrow(() -> new RuntimeException("account is not found"));
    }
    /**
     * アカウント情報の更新有無精査。
     *
     * @param accountUpdateForm 精査対象のアカウント
     * @param targetAccount     更新対象のアカウント
     * @return true:更新なし false:更新あり
     */

    public boolean isNoChange(AccountUpdateForm accountUpdateForm, Account targetAccount) {
        return accountUpdateForm.getNickname().equals(targetAccount.getNickname())
                && accountUpdateForm.getEmail().equals(targetAccount.getEmail())
                && accountUpdateForm.getDepartmentPosition().equals(targetAccount.getDepartmentPosition())
                && accountUpdateForm.getSelfIntroduction().equals(targetAccount.getSelfIntroduction());
    }
    /**
     * アカウントIDの重複精査
     */

    @Transactional(readOnly = true)
    public boolean isExistsEmail(String email) {
        // アカウントの重複精査
        int result = accountRepository.countByEmail(email);
        return result != 0;
    }

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

