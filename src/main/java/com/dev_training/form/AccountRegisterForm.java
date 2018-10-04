package com.dev_training.form;

import com.dev_training.validator.HalfAlphameric;
import com.dev_training.validator.Password;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountRegisterForm implements Serializable {

    @NotBlank
    @Size(min = 3, max = 15, message = "{error.size.min.max}")
    @HalfAlphameric
    private String accountId;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Size(max = 45, message = "{error.size.max}")
    private String name;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    @Email
    private String email;

    @NotBlank
    @Size(max = 255, message = "{error.size.max}")
    private String selfIntroduction;

    @AssertTrue(message = "パスワードが一致しません。")
    public boolean isPasswordValid() {
        return password != null && password.equals(confirmPassword);
    }
}
