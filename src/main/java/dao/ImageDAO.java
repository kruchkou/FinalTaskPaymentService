package dao;

import org.apache.commons.lang3.RandomStringUtils;

public class ImageDAO {

    public void save() {

    }


    private String randomFileName() {
        final int FILE_NAME_LENGTH = 10;
        final boolean USE_LETTERS = true;
        final boolean USE_NUMBERS = true;

        return RandomStringUtils.random(FILE_NAME_LENGTH, USE_LETTERS, USE_NUMBERS);
    }

}
