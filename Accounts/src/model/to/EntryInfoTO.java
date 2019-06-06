package model.to;

import java.sql.Date;

public class EntryInfoTO {
    private int entryid;
    private String particulars;
    private String entrytype;
    private float amount;
    private Date entrydate;
    private int accountid;
    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }
    public int getEntryid() {
        return entryid;
    }

    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getEntrytype() {
        return entrytype;
    }

    public void setEntrytype(String entrytype) {
        this.entrytype = entrytype;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }
    
}
