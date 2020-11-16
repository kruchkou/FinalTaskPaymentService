package by.epamtc.PaymentService.util.validator;

import by.epamtc.PaymentService.bean.Card;
import by.epamtc.PaymentService.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {

    private static final CardValidator instance = new CardValidator();
    private final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private CardValidator() {
    }

    public static CardValidator getInstance() {
        return instance;
    }

    public boolean validate(Card card) {
        String number = card.getNumber();
        String ownerName = card.getOwnerName();
//        String expDate = card.getExpDate();
//        String cvv = card.getCvv();
//
//
//        if (!validateNumber(number)) {
//            return false;
//        }
//        if (!validateFIO(name)) {
//            return false;
//        }
//        if (!validateFIO(surname)) {
//            return false;
//        }
//        if (!validateFIO(patronymic)) {
//            return false;
//        }
//        return validateCvv(phoneNumber);
        return true;
    }

    private boolean validateNumber(String number) {
        final String REGEXP_CARD_NUMBER = "regexp.card_number";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_CARD_NUMBER));
        Matcher matcher = pattern.matcher(number);

        return matcher.find();
    }

    private boolean validateFIO(String fio) {
        final String REGEXP_USER_FIO = "regexp.user_fio";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
        Matcher matcher = pattern.matcher(fio);

        return matcher.find();
    }

    private boolean validateCvv(String cvv) {
        final String REGEXP_CARD_CVV = "regexp.card_cvv";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_CARD_CVV));
        Matcher matcher = pattern.matcher(cvv);

        return matcher.find();
    }
}