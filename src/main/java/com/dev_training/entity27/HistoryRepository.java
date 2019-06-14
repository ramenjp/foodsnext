package com.dev_training.entity27;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HistoryRepository extends JpaRepository<History, Integer>, JpaSpecificationExecutor<History> {


    @Query(value="SELECT * FROM history WHERE ", nativeQuery = true)
    void findhistoryAccount();

}
