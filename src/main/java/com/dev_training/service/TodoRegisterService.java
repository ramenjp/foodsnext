package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO登録サービス。
 */
@Service
@Transactional
public class TodoRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TodoRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     */
    public void register(Account account, String rawPassword) {
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
     * 日付の前後有効性チェック
     * @param startDate 開始日
     * @param endDate 終了日
     * @return
     */
    public boolean isValidDate(String startDate, String endDate) {
        if (startDate == null) return false;
        if (endDate == null) return false;
        if (startDate.compareTo(endDate) >= 0) return true;
        return false;
    }
}
