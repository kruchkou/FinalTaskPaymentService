package dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class Account {

    private HashMap<Integer, String> account_type;
    private int user;
    private HashMap<Integer, String> status;
    private BigDecimal balance;
    private Date creationDate;

}
