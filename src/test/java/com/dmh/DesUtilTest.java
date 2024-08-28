package com.dmh;

import com.dmh.utils.DesUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DesUtilTest {

    @Test
    public void testEncryptDecrypt() throws Exception {
        String originalText = "Hello, World!";
        String key = "secureKey"; // 确保这是8字节的密钥

        // 加密原始文本
        String encryptedText = DesUtil.encrypt(originalText, key);
        System.out.println("Encrypted Text: " + encryptedText);
        // 确保加密后的文本不为null且与原始文本不同
        assertNotNull(encryptedText);
        assertNotEquals(originalText, encryptedText);

        // 解密加密后的文本
        String decryptedText = DesUtil.decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);

        // 验证解密后的文本是否与原始文本相同
        assertEquals(originalText, decryptedText, "Decrypted text should match the original text.");
    }
}