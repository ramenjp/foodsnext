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
@Transactional
public class AccountRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Repositoryを利用する登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     */
    public void registerByUsingRepository(Account account, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    /**
     * Native Queryによる登録処理。
     *
     * @param account     登録対象のアカウント
     * @param rawPassword 暗号化前のパスワード
     * @return アカウント
     */
    @Transactional
    public void register(Account account, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);

        String sql = "INSERT INTO Accounts (account_id, password, name, email, self_introduction) VALUES( :accountId, :password, :name, :email, :selfIntroduction)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("accountId", account.getAccountId());
        query.setParameter("name", account.getName());
        query.setParameter("email", account.getEmail());
        query.setParameter("selfIntroduction", account.getSelfIntroduction());
        query.setParameter("password", account.getPassword());
        query.executeUpdate();
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
}
