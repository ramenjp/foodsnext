package com.dev_training.controller;

import com.dev_training.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * アカウントプロフィール画像アップロードコントローラ。
 */
@Controller
@RequestMapping(value = "/account/upload")
public class AccountProfileImageUploadController {

    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public AccountProfileImageUploadController(HttpSession session) {
        this.session = session;
    }

    @RequestMapping(path = "/init")
    String init(Model model) {
        return "account/accountProfileImageUploadForm";
    }


    @RequestMapping(path = "/upload")
    String upload(Model model, @RequestParam("upload_file") MultipartFile multipartFile) {
        // ファイルが空の場合は異常終了
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("empty file.");
        }

        // 1Mを超えるファイルはエラー
        if (multipartFile.getSize() > 1048576) {
            throw new RuntimeException("file size over.");
        }

        // ファイル格納先ディレクトリの作成
        StringBuffer filePath = new StringBuffer("C:/upload");
        File uploadDir = mkdirs(filePath);

        // アップロードファイルを置く
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        File uploadFile = new File(uploadDir.getPath() + "/" + account.getId() + "_profile");
        if (uploadFile.exists()){
            uploadFile.delete();
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
            uploadFileStream.write(bytes);
            uploadFileStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "account/accountProfileImageUploadForm";
    }

    /**
     * アップロードファイルを格納するディレクトリを作成する
     *
     * @param filePath ファイルパス
     * @return File
     */
    private File mkdirs(StringBuffer filePath) {
        File uploadDir = new File(filePath.toString());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }
}
