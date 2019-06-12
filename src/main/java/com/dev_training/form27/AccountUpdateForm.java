package com.dev_training.form27;

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
public class AccountUpdateForm implements Serializable {

    @NotBlank
    @Size(min = 3, max = 45, message = "{error.size.max}")
    private String nickname;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    @Email
    private String email;

    @NotBlank
    @Size(max = 100, message = "{error.size.max}")
    private String department_position;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String selfIntroduction;


    @NotBlank
    private String currentPassword;

    @NotBlank
    @Password
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "パスワードが一致しません。")
    public boolean isPasswordValid() {
        return Objects.nonNull(newPassword) && newPassword.equals(confirmPassword);
    }

    @AssertTrue(message = "パスワードが変更されていません。")
    public boolean isNewPasswordValid() {
        return Objects.nonNull(currentPassword) && !currentPassword.equals(newPassword);
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
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

    public void setDepartment_position(String department_position) {
        this.department_position = department_position;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewpassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewpassword(String newpassword) {
        this.newPassword = newpassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
