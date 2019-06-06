package model.to;

import java.sql.Timestamp;

public class AccountInfoTO {
    private int accountid;
    private String accountname;
    private float openingbalance;
    private Timestamp creationdate;
    private float currentbalance;

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public float getOpeningbalance() {
        return openingbalance;
    }

    public void setOpeningbalance(float openingbalance) {
        this.openingbalance = openingbalance;
    }

    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    public float getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(float currentbalance) {
        this.currentbalance = currentbalance;
    }
    public String toString(){
        return accountname;
    }
}
