package com.dev_training.form;

import com.dev_training.validator.HalfAlphameric;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountUpdateForm implements Serializable {
    @NotBlank
    @Size(min = 3, max = 15, message = "{error.size.min.max}")
    @HalfAlphameric
    private String accountId;

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

}
