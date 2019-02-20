package com.dev_training.form;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * コメント検索フォーム。
 */
public class CommentSearchForm implements Serializable {

    private int id;

    private int accountId;

    private String name;

    private String comment;

    private String createdTms;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setComment(String comment) { this.comment = comment; }

    public String getComment() { return comment; }

    public String getCreatedTms() { return createdTms; }

    public void setCreatedTms(String createdTms) { this.createdTms = createdTms; }
}
