package util;

import util.exception.HashException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class StringHasher {

    private static StringHasher instance;

    public StringHasher getInstance() {
        if (instance == null) {
            instance = new StringHasher();
        }
        return instance;
    }

    private StringHasher() {
    }

    private byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public byte[] getHash(String password, byte[] salt) throws HashException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        byte[] hash = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new HashException("No such algorithm exception", e);
        } catch (InvalidKeySpecException e) {
            throw new HashException("Invalid KeySpec exception", e);
        }
        return hash;
    }

}
