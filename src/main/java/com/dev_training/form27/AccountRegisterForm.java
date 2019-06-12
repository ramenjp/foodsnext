package com.dev_training.form27;


import com.dev_training.entity27.Account;
import com.dev_training.validator.HalfAlphameric;
import com.dev_training.validator.Password;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * アカウント登録フォーム。
 */
public class AccountRegisterForm implements Serializable {

    @NotBlank
    @Size(min = 3, max = 45, message = "{error.size.max}")
    private String nickname;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")

    @Email
    private String email;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Size(max = 100, message = "{error.size.max}")
    private String department_position;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String selfIntroduction;

    @AssertTrue(message = "パスワードが一致しません。")
    public boolean isPasswordValid() {
        return Objects.nonNull(password) && password.equals(confirmPassword);
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getDepartment_position() {
        return department_position;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setDepartment_position(String department_position) {
        this.department_position = department_position;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }
}
