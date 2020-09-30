package entity;

import java.util.HashMap;

public class User extends SignUpUser {

    private HashMap<Integer, String> role;

    public HashMap<Integer, String> getRole() {
        return role;
    }

    public void setRole(HashMap<Integer, String> role) {
        this.role = role;
    }

}
