package com.dev_training.controller27;

import com.dev_training.entity.Account;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

public class ImageController {
    /** リソースローダー */
    private final ResourceLoader resourceLoader;
    /** アプリケーション環境設定 */
    private Environment environment;
    /** HTTPセッション */
    private final HttpSession session;
    /** セッションキー(ログインユーザのアカウント) */
    private static final String SESSION_FORM_ID = "account";

    @Autowired
    public ImageController(ResourceLoader resourceLoader, Environment environment, HttpSession session) {
        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.session = session;
    }

    /**
     * アカウントのemailに紐づくプロフィール画像を取得し、バイナリで返却する。
     *
     * @param email email
     * @return プロフィール画像のバイナリ
     * @throws IOException 入出力例外
     */
    @ResponseBody
    @RequestMapping(value = "/image/profile", method = {RequestMethod.GET })
    public HttpEntity<byte[]> getImage(@RequestParam("email") String email) throws IOException {
        return getHttpEntity(email);
    }

    /**
     * ログインユーザのプロフィール画像を取得し、バイナリで返却する。
     *
     * @return プロフィール画像のバイナリ
     * @throws IOException 入出力例外
     */
    @ResponseBody
    @RequestMapping(value = "/image/myprofile", method = {RequestMethod.GET })
    public HttpEntity<byte[]> getMyImage() throws IOException {
        Account account = (Account) session.getAttribute(SESSION_FORM_ID);
        return getHttpEntity(String.valueOf(account.getEmail()));
    }

    /**
     * アカウントのEmailに紐づくプロフィール画像を取得し、バイナリで返却する。
     *
     * @param email email
     * @return プロフィール画像のバイナリ
     * @throws IOException 入出力例外
     */
    private HttpEntity<byte[]> getHttpEntity(String email) throws IOException {
        // リソースファイルを読み込み
        String dirPath = environment.getProperty("upload.dir.path");
        Resource resource = resourceLoader.getResource("file:" + dirPath + "/" + email + "_profile");
        // 存在しなければ、noimageを格納する
        if (!resource.exists()) {
            resource = resourceLoader.getResource("classpath:" + "/static/images/noimage");
        }

        // 読み込み、バイナリ変換
        InputStream image = resource.getInputStream();
        byte[] b = IOUtils.toByteArray(image);
        // レスポンスデータとして返却
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(b.length);
        return new HttpEntity<>(b, headers);
    }
}
