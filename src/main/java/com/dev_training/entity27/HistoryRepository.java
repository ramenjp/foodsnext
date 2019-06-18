package com.dev_training.entity27;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer>, JpaSpecificationExecutor<History> {


    @Query(value = "select accounts.account_id,accounts.nickname, accounts.department_position ,accounts.self_introduction accounts ,history where accounts.account_id=history.history_partner_id", nativeQuery = true)
    List<History> findHistoryAccount(@Param("account_id")int account_id, @Param("history_partner_id") int history_partner_id);

    @Query(value = "select * from history where account_id = :account_id", nativeQuery = true)
    List<History> findHistoryByAccountId(@Param("account_id")int account_id);

}
