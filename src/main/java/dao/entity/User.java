package dao.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User extends SignUpUser {

    private HashMap<Integer, String> role;

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
                "login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role='" + role + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
}
