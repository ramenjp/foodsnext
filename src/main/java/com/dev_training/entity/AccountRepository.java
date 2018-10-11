package com.dev_training.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
    Account findByAccountId(String accountId);

    /**
     * アカウントIDに紐づくアカウントの件数を取得する。
     *
     * @param accountId アカウントID
     * @return 件数
     */
    int countByAccountId(String accountId);
}