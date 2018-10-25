package com.dev_training.controller;

import com.dev_training.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

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
    /** メッセージソース */
    private final MessageSource messageSource;

    @Autowired
    public AccountProfileImageUploadController(HttpSession session, MessageSource messageSource) {
        this.session = session;
        this.messageSource = messageSource;
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
//            model.addAttribute("errorMsg", messageSource.getMessage("validation.file.select", null, Locale.JAPAN));
//            return "account/accountProfileImageUploadForm";
        }

        // 1Mを超えるファイルはエラー
        long fileSize = multipartFile.getSize();
        if (fileSize > 1048576) {
            throw new RuntimeException("file size over.");
//            model.addAttribute("errorMsg", messageSource.getMessage("validation.file.invalid.size", null, Locale.JAPAN));
//            return "account/accountProfileImageUploadForm";
        }

        // ファイル格納先ディレクトリの作成
        StringBuffer filePath = new StringBuffer("C:\\/upload");
        File uploadDir = mkdirs(filePath);

        try {
            // アップロードファイルを置く
            String fileName = multipartFile.getOriginalFilename();
            File uploadFile = new File(uploadDir.getPath() + "/" + fileName);
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
            uploadFileStream.write(bytes);
            uploadFileStream.close();

            return "account/accountProfileImageUploadForm";

        } catch (Throwable t) {
//            t.printStackTrace();
            throw new RuntimeException(t);
//            model.addAttribute("errorMsg", messageSource.getMessage("validation.file.invalid.upload ", null, Locale.JAPAN));
//            return "account/accountProfileImageUploadForm";
        }
    }

    /**
     * アップロードファイルを格納するディレクトリを作成する
     *
     * @param filePath ファイルパス
     * @return File
     */
    private File mkdirs(StringBuffer filePath) {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        File uploadDir = new File(filePath.toString(), String.valueOf(account.getId()));
        if (uploadDir.exists()) {
            for (File child : Objects.requireNonNull(uploadDir.listFiles())) {
                child.delete();
            }
        } else {
            uploadDir.mkdirs();
        }
        return uploadDir;
    }
}
