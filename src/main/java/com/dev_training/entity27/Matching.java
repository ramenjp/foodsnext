package com.dev_training.entity27;


import javax.persistence.*;
import java.util.Date;

/**
 * マッチングエンティティ。
 */

@Entity
@Table(name = "matching")
public class Matching{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    @Column(name = "matching_no")
    private int matchingNo;

    @Column(name = "matching_date", nullable = false)
    private Date matchingDate;

    @Column(name = "shuffle_no", nullable = false)
    private int shuffleNo;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMatchingNo() {
        return matchingNo;
    }

    public void setMatchingNo(int matchingNo) {
        this.matchingNo = matchingNo;
    }

    public Date getMatchingDate() {
        return matchingDate;
    }

    public void setMatchingDate(Date matchingDate) {
        this.matchingDate = matchingDate;
    }

    public int getShuffleNo() {
        return shuffleNo;
    }

    public void setShuffleNo(int shuffleNo) {
        this.shuffleNo = shuffleNo;
    }
}