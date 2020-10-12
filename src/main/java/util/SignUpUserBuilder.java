package util;

import dao.entity.SignUpUser;
import util.exception.BuildException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignUpUserBuilder {

    SignUpUser user = new SignUpUser();

    public void setLogin(String login) {
        user.setLogin(login);
    }

    public void setPassword(String password) {
        user.setPassword(password); //заменить на хэш
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

    public void setBirthDate(String birthDate) throws BuildException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthDate(df.parse(birthDate));
        } catch (ParseException e) {
            throw new BuildException("Cant parse birthdate", e);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
    }

    public SignUpUser build() {
        return user;
    }

}
