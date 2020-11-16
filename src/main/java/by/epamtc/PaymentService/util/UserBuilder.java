package by.epamtc.PaymentService.util;

import by.epamtc.PaymentService.bean.User;

import java.text.ParseException;

public class UserBuilder {

    private final DateParser dateParser = DateParser.getInstance();

    private User user = new User();

    public void setLogin(String login) {
        user.setLogin(login);
    }

    public void setPassword(String password) {
        user.setPassword(password);
    }

    public void setName(String name) {
        user.setName(name);
    }

    public void setSurname(String surname) {
        user.setSurname(surname);
    }

    public void setPatronymic(String patronymic) {
        user.setPatronymic(patronymic);
    }

    public void setId(int id) {
        user.setId(id);
    }

    public void setBirthDate(String birthDate) throws ParseException {
            user.setBirthDate(dateParser.parse(birthDate));
    }

    public void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
    }

    public User build() {
        return user;
    }

}
