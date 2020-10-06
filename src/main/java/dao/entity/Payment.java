package dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class Payment {

    private int id;
    private HashMap<Integer, String> type;
    private Date date;
    private int cardFrom;
    private int accountTo;
    private BigDecimal amount;
    private String comment;

}
