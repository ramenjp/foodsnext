package com.dev_training.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * コメントテーブル結合用エンティティ。
 */
@Entity
public class ExtendedComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_id", nullable = false, unique = true)
    private int accountId;

    @Column(name = "name", nullable = false)
    private String name;

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

    public int getAccountId() { return accountId; }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedTms() { return createdTms; }

    public void setCreatedTms(String createdTms) { this.createdTms = createdTms; }
}