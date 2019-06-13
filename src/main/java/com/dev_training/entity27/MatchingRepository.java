package com.dev_training.entity27;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Integer>, JpaSpecificationExecutor<Matching> {

    @Query(value="SELECT random_no FROM matching ORDER BY matching_no ASC", nativeQuery = true)
    void SortAscendingOrder();

    @Query(value="SELECT * FROM matching WHERE ", nativeQuery = true)
    void findMatchingAccount();

    //日付が今日のアカウントをリストで取り出す
    @Query(value = "SELECT * FROM matching WHERE date = :date", nativeQuery = true)
    List<Matching> findTodayAccount();

    @Query(value = "INSERT INTO matching (matching_no) VALUES (matchingNo)", nativeQuery = true)
    void insertMatchingNo(@Param("matchingNo")int matchingNo);

    @Query(value = "SELECT mutching_no FROM matching WHERE account_id=:accountId AND date = :date", nativeQuery = true)
    int findMyMatchingNo(@Param("accountId,date") int accountId,Date date);

    @Query(value = "SELECT account_id FROM matching WHERE account_id != :accountId AND matching_no = :matchingNo", nativeQuery = true)
    int findMyMatchingParnerNo(@Param("accountId,matchingNo")int accountId,int matchingNo);

}