package by.epamtc.paymentservice.service.validator;

import java.math.BigDecimal;

public class PaymentValidator {

    private static final PaymentValidator instance = new PaymentValidator();

    private PaymentValidator() {
    }

    public static PaymentValidator getInstance() {
        return instance;
    }

    public boolean validateAmount(BigDecimal amount) {
        return greaterThanZero(amount);
    }

    private boolean greaterThanZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

}
