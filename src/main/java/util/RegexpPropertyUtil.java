package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegexpPropertyUtil {

    private static RegexpPropertyUtil instance;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("regexp", Locale.getDefault());

    private RegexpPropertyUtil() {
    }

    public static RegexpPropertyUtil getInstance() {
        if (instance == null) {
            instance = new RegexpPropertyUtil();
        }
        return instance;
    }

    public String getProperty(String propertyName) {
        return resourceBundle.getString(propertyName);
    }

}

