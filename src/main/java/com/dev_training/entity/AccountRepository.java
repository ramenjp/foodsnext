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
     * アカウントのIDに紐づくアカウントを検索する。
     *
     * @param id アカウントのID
     * @return アカウント
     */
    @Query(value = "SELECT name FROM accounts WHERE id = :id ", nativeQuery = true)
    String findAccountNameById(@Param("id") int id);

    /**
     * アカウントIDに紐づくアカウントを検索する。
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    Account findByAccountId(String accountId);

    /**
     * アカウントIDに紐づくアカウントの件数を取得する。
     *
     * @param accountId アカウントID
     * @return 件数
     */
    int countByAccountId(String accountId);

    /**
     * IDに紐づくアカウントを削除する。
     *
     * @param id ID
     */
    void deleteById(Integer id);
}