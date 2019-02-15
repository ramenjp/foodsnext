package com.dev_training.controller;

import com.dev_training.entity.Account;
import com.dev_training.form.CommentRegisterForm;
import com.dev_training.service.CommentRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * コメント登録コントローラ。
 */
@Controller
@RequestMapping(value = "/comment/register")
public class CommentRegisterController {

    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";
    /** アカウント登録サービス */
    private final CommentRegisterService service;

    @Autowired
    public CommentRegisterController(HttpSession session, CommentRegisterService commentRegisterService) {
        this.session = session;
        this.service = commentRegisterService;
    }

    @RequestMapping(path = "/init")
    String init(Model model) {
        return "top/topForm";
    }


    @RequestMapping(path = "/register")
    String register(@RequestBody CommentRegisterForm commentRegisterForm) {


        return "account/accountProfileImageUploadForm";
    }

}
