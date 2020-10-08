package dao.entity;

import java.util.HashMap;
import java.util.Map;

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



}
