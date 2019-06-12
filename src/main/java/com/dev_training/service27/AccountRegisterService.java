package com.dev_training.service27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import com.dev_training.form27.AccountRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント登録サービス
 */
@Service
public class AccountRegisterService {

    /**
     * アカウントレポジトリ
     */
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {


        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * 登録処理
     *
     * @param account アカウントデータ
     */

    @Transactional
    public void register(Account account, String rawPassword) {
        String encodedPassword= passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    /**
     * アカウントID重複精査
     *
     * @param email
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String email) {
        int result = accountRepository.countByEmail(email);
        return result != 0;
    }

}