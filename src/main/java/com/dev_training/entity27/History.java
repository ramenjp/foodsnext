package com.dev_training.entity27;


import javax.persistence.*;
import java.util.Date;

/**
 * マッチングエンティティ。
 */

@Entity
@Table(name = "history")
public class History{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    @Column(name = "history_partner_id", nullable = false)
    private int historyPartnerId;


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

    public int getHistoryPartnerId() {
        return historyPartnerId;
    }

    public void setHistoryPartnerId(int historyPartnerId) {
        this.historyPartnerId = historyPartnerId;
    }
}