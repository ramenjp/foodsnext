package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * アカウント登録サービス。
 */
@Service
public class AccountRegisterService {

    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;
    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder;
    /** エンティティマネージャ */
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     */
    @Transactional
    public void register(Account account, String rawPassword) {
        // パスワードの暗号化
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
//        String sql = "INSERT INTO Accounts (account_id, password, name, email, self_introduction) VALUES( :accountId, :password, :name, :email, :selfIntroduction)";
//        Query query = entityManager.createNativeQuery(sql);
//        query.setParameter("accountId", account.getAccountId());
//        query.setParameter("name", account.getName());
//        query.setParameter("email", account.getEmail());
//        query.setParameter("selfIntroduction", account.getSelfIntroduction());
//        query.setParameter("password", account.getPassword());
//        query.executeUpdate();
    }

    /**
     * アカウントIDの重複精査。
     *
     * @param accountId 精査対象のアカウントID
     * @return true:未存在 false:存在
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String accountId) {
        int result = accountRepository.countByAccountId(accountId);
        return result != 0;
    }
}
