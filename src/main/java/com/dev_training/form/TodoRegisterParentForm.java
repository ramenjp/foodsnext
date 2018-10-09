package com.dev_training.form;

import com.dev_training.entity.Account;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Todo登録親フォーム。
 */
public class TodoRegisterParentForm implements Serializable {

    /**
     * TODO登録フォーム
     */
    @Valid
    private TodoRegisterForm todoRegisterForm;
    /**
     * 担当者選択用アカウントリスト
     */
    private List<Account> accountList;
    /**
     * 優先度
     */
    @Value("${todo.priority}")
    private Map<String, String> allPriority;
    /**
     * ステータス
     */
    @Value("${todo.status}")
    private Map<String, String> allStatus;

    public TodoRegisterForm getTodoRegisterForm() {
        return todoRegisterForm;
    }

    public void setTodoRegisterForm(TodoRegisterForm todoRegisterForm) {
        this.todoRegisterForm = todoRegisterForm;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Map<String, String> getAllPriority() {
        return allPriority;
    }

    public void setAllPriority(Map<String, String> allPriority) {
        this.allPriority = allPriority;
    }

    public Map<String, String> getAllStatus() {
        return allStatus;
    }

    public void setAllStatus(Map<String, String> allStatus) {
        this.allStatus = allStatus;
    }
}
