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
    @Query(value = "SELECT * FROM matching WHERE matching_date = :date", nativeQuery = true)
    List<Matching> findTodayAccount();

    @Query(value = "INSERT INTO matching (matching_no) VALUES (:matching_no)", nativeQuery = true)
    void insertMatchingNo(@Param("matching_no")int matching_no);

    @Query(value = "SELECT matching_no FROM matching WHERE account_id=:account_id AND matching_date = :matching_date", nativeQuery = true)
    Matching findMyMatchingNo(@Param("account_id") int account_id, @Param("matching_date") Date matching_date);

    @Query(value = "SELECT account_id FROM matching WHERE account_id != :account_id AND matching_no = :matching_no", nativeQuery = true)
    Matching findMyMatchingPartnerNo(@Param("account_id")int account_id,@Param("matching_no") int matching_no);

}