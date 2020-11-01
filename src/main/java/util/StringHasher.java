package util;

import org.mindrot.jbcrypt.BCrypt;

public class StringHasher {

    private static StringHasher instance = new StringHasher();

    private StringHasher() {
    }

    public static StringHasher getInstance() {
        return instance;
    }

    public boolean checkHash(String string, String hash) {
        return BCrypt.checkpw(string, hash);
    }

    public String getHash(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt());
    }

}
