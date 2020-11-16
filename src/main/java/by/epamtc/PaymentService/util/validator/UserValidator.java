package by.epamtc.PaymentService.util.validator;

import by.epamtc.PaymentService.bean.SignUpData;
import by.epamtc.PaymentService.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final UserValidator instance = new UserValidator();
    private final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        return instance;
    }

    public boolean validate(SignUpData signUpData) {
        String login = signUpData.getLogin();
        String name = signUpData.getName();
        String surname = signUpData.getSurname();
        String patronymic = signUpData.getPatronymic();
        String phoneNumber = signUpData.getPhoneNumber();

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
        final String REGEXP_LOGIN = "regexp.login";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_LOGIN));
        Matcher matcher = pattern.matcher(login);

        return matcher.find();
    }

    private boolean validateFIO(String fio) {
        final String REGEXP_USER_FIO = "regexp.user_fio";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
        Matcher matcher = pattern.matcher(fio);

        return matcher.find();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        final String REGEXP_PHONE_NUM = "regexp.phone_number";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_PHONE_NUM));
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }
}