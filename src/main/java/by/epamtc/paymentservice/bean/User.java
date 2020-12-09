package by.epamtc.paymentservice.bean;

import java.io.Serializable;
import java.util.Objects;

public class User extends SignUpData implements Serializable {

    private static final long serialVersionUID = -4636462495979575713L;

    private int id;
    private Status status;
    private String imageSrc;

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
                "login='" + getLogin() + '\'' +
                ", password='" + getPassword() + '\'' +
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
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(status, user.status) &&
                Objects.equals(imageSrc, user.imageSrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
