package com.dev_training.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * イメージコントローラ。
 */
@RestController
public class ImageController {

    /** リソースローダー */
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * アカウントのIDに紐づくプロフィール画像を取得し、バイナリで返却する。
     *
     * @param id id
     * @return プロフィール画像のバイナリ
     * @throws IOException 入出力例外
     */
    @ResponseBody
    @RequestMapping(value = "/image/profile", method = {RequestMethod.GET })
    public HttpEntity<byte[]> getImage(@RequestParam("id") String id) throws IOException {
        // リソースファイルを読み込み
        Resource resource = resourceLoader.getResource("file:C:/upload/" + id + "_profile");
        // 存在しなければ、後続処理を行わない。
        if (!resource.exists()) {
            return null;
        }

        // 読み込み、byteへ変換
        InputStream image = resource.getInputStream();
        byte[] b = IOUtils.toByteArray(image);
        // レスポンスデータとして返却
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(b.length);
        return new HttpEntity<byte[]>(b, headers);
    }
}
