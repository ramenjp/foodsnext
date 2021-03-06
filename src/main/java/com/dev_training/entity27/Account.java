package com.dev_training.entity27;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * アカウントエンティティ。
 */
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private int accountId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "department_position", nullable = false, length = 255)
    private String departmentPosition;

    @Column(name = "self_introduction", nullable = false, length = 255)
    private String selfIntroduction;


    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentPosition() {
        return departmentPosition;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public boolean getDeleteFlag() {
        return deleteFlag;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartmentPosition(String departmentPosition) {
        this.departmentPosition = departmentPosition;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }


    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
