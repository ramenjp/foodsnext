package com.dev_training.entity27;

import com.dev_training.entity27.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * アカウントリポジトリ。
 */
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    /**
     * emailに紐づくアカウントを検索する。
     *
     * @param email
     * @return アカウント
     */
    @Query(value = "SELECT * FROM accounts WHERE email = :email AND delete_flag = 0", nativeQuery = true)
    com.dev_training.entity27.Account findByAccountId(@Param("email") String email);

    /**
     * EmailIDに紐づくアカウントの件数を取得する。
     *
     * @param email
     * @return 件数
     */
    @Query(value = "SELECT COUNT(*) FROM accounts WHERE email = :email AND delete_flag = 0", nativeQuery = true)
    int countByAccount(@Param("email") String email);

    /**
     * 全アカウントを検索する。
     *
     * @return 全アカウントのリスト
     */
    @Query(value = "SELECT * FROM accounts WHERE delete_flag = 0", nativeQuery = true)
    List<Account> findAllAccount();
}