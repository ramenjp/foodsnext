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
    private String departmentPosition;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String selfIntroduction;



    public String getNickname() {
        return nickname;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

}
