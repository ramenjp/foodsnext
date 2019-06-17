package com.dev_training.entity27;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Integer>, JpaSpecificationExecutor<Matching> {

    @Query(value="SELECT * FROM matching WHERE ", nativeQuery = true)
    void findMatchingAccount();

    //日付が今日のアカウントをリストで取り出す
    @Query(value = "SELECT * FROM matching WHERE matching_date = :matching_date ORDER BY shuffle_no ASC", nativeQuery = true)
    List<Matching> findTodayAccount(@Param("matching_date")String nowDate);

    @Query(value = "UPDATE matching SET matching_no = :matching_no", nativeQuery = true)
    void insertMatchingNo(@Param("matching_no")int matching_no);

    //日付が今日の自分のアカウントを取り出す
    @Query(value = "SELECT * FROM matching WHERE account_id=:account_id AND matching_date = :matching_date", nativeQuery = true)
    Matching findMyMatchingNo(@Param("account_id") int account_id, @Param("matching_date") String matching_date);

    @Query(value = "SELECT * FROM matching WHERE account_id != :account_id AND matching_no = :matching_no AND matching_date = :matching_date", nativeQuery = true)
    Matching findMyMatchingPartnerNo(@Param("account_id")int account_id,@Param("matching_no") int matching_no,@Param("matching_date") String matching_date);
    //日付が違うものという条件を付ける

    @Query(value = "SELECT * FROM matching WHERE account_id=:account_id AND matching_date = :matching_date", nativeQuery = true)
    Matching findMatchingAccount(@Param("account_id") int account_id, @Param("matching_date") String matching_date);
}