package dao.entity;

import java.util.HashMap;
import java.util.Objects;

public class User extends SignUpUser {

    private int id;
    private HashMap<Integer, String> role;
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

    public HashMap<Integer, String> getRole() {
        return role;
    }

    public void setRole(HashMap<Integer, String> role) {
        this.role = role;
    }

    public String getRoleName() {
        String roleName = null;
        for (String value : role.values()) {
            roleName = value;
        }
        return roleName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.getId() + '\'' +
                "login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role='" + role + '\'' +
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
                Objects.equals(role, user.role) &&
                Objects.equals(imageSrc, user.imageSrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
}
