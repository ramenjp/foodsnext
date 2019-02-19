package com.dev_training.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * アカウントリポジトリ。
 */
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    /**
     * アカウントIDに紐づくアカウントを検索する。
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    @Query(value = "SELECT * FROM accounts WHERE account_id = :accountId ", nativeQuery = true)
    Account findByAccountId(@Param("accountId") String accountId);
//    Account findByAccountId(String accountId);

    /**
     * アカウントIDに紐づくアカウントの件数を取得する。
     *
     * @param accountId アカウントID
     * @return 件数
     */
    @Query(value = "SELECT COUNT(*) FROM accounts WHERE account_id = :accountId ", nativeQuery = true)
    int countByAccountId(@Param("accountId") String accountId);
//    int countByAccountId(String accountId);

    /**
     * IDに紐づくアカウントを削除する。
     *
     * @param id ID
     */
//    @Query(value = "SELECT COUNT(*) FROM accounts WHERE account_id = :accountId ", nativeQuery = true)
//    void deleteById(@Param("id") int id);
    void deleteById(Integer id);
}