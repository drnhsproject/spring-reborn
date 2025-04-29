package id.co.xinix.spring.services;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    private SecretKeySpec createSecretKey(String pin) {
        byte[] key = (pin + generateSalt()).getBytes();
        return new SecretKeySpec(key, "AES");
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String encrypt(String data, String pin) throws Exception {
        SecretKeySpec secretKey = createSecretKey(pin);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData, String pin) throws Exception {
        SecretKeySpec secretKey = createSecretKey(pin);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedBytes);
        return new String(decryptedData);
    }
}
