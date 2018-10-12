package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.form.AccountUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/*** アカウント情報更新サービス。*/
@Service
public class AccountUpdateService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountUpdateService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * アカウント最新データ取得処理。
     *
     * @param id 　更新対象のアカウントID
     * @return アカウント
     */
    @Transactional(readOnly = true)
    public Account getAccountById(int id) {
        String jpql = "SELECT a FROM Account a WHERE a.id = :id";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     * アカウント情報の更新有無精査。
     *
     * @param accountUpdateForm 精査対象のアカウント
     * @param targetAccount     更新対象のアカウント
     * @return true:更新なし false:更新あり
     */

    public boolean isNoChange(AccountUpdateForm accountUpdateForm, Account targetAccount) {
        return accountUpdateForm.getAccountId().equals(targetAccount.getAccountId())
                && accountUpdateForm.getName().equals(targetAccount.getName())
                && accountUpdateForm.getEmail().equals(targetAccount.getEmail())
                && accountUpdateForm.getSelfIntroduction().equals(targetAccount.getSelfIntroduction());
    }

    /**
     * アカウントIDの重複精査。
     *
     * @param accountId 精査対象のアカウントID
     * @return true:存在 false:未存在
     */
    @Transactional(readOnly = true)
    public boolean isExistsAccountId(String accountId) {
        // アカウントIDの重複精査
        int result = accountRepository.countByAccountId(accountId);
        return result != 0;
    }

    /**
     * 更新処理。
     *
     * @param account 更新対象のアカウント
     * @return 更新件数
     */
    @Transactional
    public int updateAccountById(Account account) {
        String jpql = "UPDATE Account a SET a.accountId = :accountId, a.name = :name, a.email = :email, a.selfIntroduction = :selfIntroduction WHERE a.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("accountId", account.getAccountId());
        query.setParameter("name", account.getName());
        query.setParameter("email", account.getEmail());
        query.setParameter("selfIntroduction", account.getSelfIntroduction());
        query.setParameter("id", account.getId());
        return query.executeUpdate();
    }

}