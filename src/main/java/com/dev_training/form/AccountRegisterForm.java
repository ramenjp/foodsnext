package com.dev_training.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountRegisterForm implements Serializable {
    private String accountId;
    private String password;
    private String name;
    private String email;
    private String selfIntroduction;
}
