package com.example.studentmanagementsystemandroidapp.utils;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class EncryptionUtils {
    public static String encrypt(String text, String key) {
        try {
            return AESCrypt.encrypt(key, text);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

    public static String decrypt(String encryptedText, String key) {
        try {
            return AESCrypt.decrypt(key, encryptedText);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

}
