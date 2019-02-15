package com.dev_training.entity;

import javax.persistence.*;

/**
 * コメントエンティティ。
 */
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_id", nullable = false, unique = true)
    private String accountId;

    @Column(name = "comment", nullable = false, length = 200)
    private String comment;

    @Column(name = "created_tms", nullable = false)
    private String createdTms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedTms() { return createdTms; }

    public void setCreatedTms(String createdTms) { this.createdTms = createdTms; }
}