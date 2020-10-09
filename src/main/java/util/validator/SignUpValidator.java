package util.validator;

import dao.entity.SignUpUser;
import util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidator {

    private static final SignUpValidator instance = new SignUpValidator();
    private final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private SignUpValidator() {
    }

    public static SignUpValidator getInstance() {
        return instance;
    }

    public boolean validate(SignUpUser signUpUser) {
        String login = signUpUser.getLogin();
        String name = signUpUser.getName();
        String surname = signUpUser.getSurname();
        String patronymic = signUpUser.getPatronymic();
        String phoneNumber = signUpUser.getPhoneNumber();

        if (!validateLogin(login)) {
            return false;
        }
        if (!validateFIO(name)) {
            return false;
        }
        if (!validateFIO(surname)) {
            return false;
        }
        if (!validateFIO(patronymic)) {
            return false;
        }
        return validatePhoneNumber(phoneNumber);
    }

    private boolean validateLogin(String login) {
        final int MIN_LOGIN_LENGTH = 6;
        final int MAX_LOGIN_LENGTH = 16;

        if (login == null) {
            return false;
        }

        return login.length() >= MIN_LOGIN_LENGTH && login.length() <= MAX_LOGIN_LENGTH;
    }

    private boolean validateFIO(String text) {
        final int MIN_FIO_LENGTH = 2;
        final int MAX_FIO_LENGTH = 20;

        if (text == null) {
            return false;
        }
        return text.length() >= MIN_FIO_LENGTH && text.length() <= MAX_FIO_LENGTH;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        final int PHONE_NUM_LENGTH = 13;
        final String REGEX_PHONE_NUM = "regexp.phone_number";

        if (phoneNumber.length() != PHONE_NUM_LENGTH) {
            return false;
        }

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEX_PHONE_NUM));
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }
}