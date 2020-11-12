package dao.entity;

import java.util.Date;
import java.util.Objects;

public class SignUpData extends LoginData {

    private String name;
    private String surname;
    private String patronymic;
    private Date birthDate;
    private String phoneNumber;

    public SignUpData() {
    }

    public SignUpData(String name, String surname, String patronymic, Date birthDate, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignUpData)) return false;
        if (!super.equals(o)) return false;
        SignUpData that = (SignUpData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public String toString() {
        return "SignUpData{" +
                "login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, patronymic, birthDate, phoneNumber);
    }
}
