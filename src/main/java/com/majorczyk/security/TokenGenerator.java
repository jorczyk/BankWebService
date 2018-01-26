package com.majorczyk.security;

import com.majorczyk.database.UserRepository;
import com.majorczyk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Token generator for provided credentials
 */
@Component
public class TokenGenerator {

    @Autowired
    UserRepository userRepository;

    private byte[] key;

    private static final String ALGORITHM = "AES";

    public TokenGenerator()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        key = bytes;
    }

    /**
     * Encrypts the given plain text
     * @param login The plain text to encrypt
     */
    public String encrypt(String login) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return new String(cipher.doFinal(login.getBytes()));
    }

    /**
     * Decrypts the given byte array
     * @param token The data to decrypt
     */
    public String decrypt(String token) throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(cipher.doFinal(token.getBytes()));
    }

    public boolean validateToken(String token) {
        try {
            String login = decrypt(token);
            User user = userRepository.findByLogin(login);
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
