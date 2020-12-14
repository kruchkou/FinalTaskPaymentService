package by.epamtc.paymentservice.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = -4636462495979575713L;

    private int id;
    private String login;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthDate;
    private String phoneNumber;
    private Status status;
    private String imageSrc;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", login='" + getLogin() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", imageSrc='" + getImageSrc() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                login.equals(user.login) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                patronymic.equals(user.patronymic) &&
                birthDate.equals(user.birthDate) &&
                phoneNumber.equals(user.phoneNumber) &&
                status.equals(user.status) &&
                Objects.equals(imageSrc, user.imageSrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, patronymic, birthDate, phoneNumber, status, imageSrc);
    }
}
