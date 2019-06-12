package com.dev_training.service27;

import com.dev_training.entity27.Account;
import com.dev_training.entity27.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント登録サービス
 */
@Service
public class AccountRegisterService {

    /** アカウントレポジトリ　*/
    private final AccountRepository accountRepository;

    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;}

    /**
     * 登録処理
     *
     * @param account アカウントデータ
     * @param rawPassword 暗号化前のパスワード
     */

    @Transactional
    public void register(Account account, String rawPassword) {
        // パスワードの暗号化
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }
    /**
     * アカウントID重複精査
     * @param email 精査対象のメール
     * @return true:未存在 false:存在
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String email){
        int result = accountRepository.countByEmail(email);
        return result !=0;
    }

}