package com.dev_training.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5ハッシュ暗号化Util。
 */
public class MD5Util {

    /**
     * 文字列をハッシュ暗号化する。
     *
     * @param message メッセージ
     * @return ハッシュメッセージ
     */
    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Illegal State.");
        }
    }

    /**
     * バイト配列をハッシュ化する。
     *
     * @param array バイト配列
     * @return 結果
     */
    private static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte anArray : array) {
            sb.append(Integer.toHexString((anArray & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }
}