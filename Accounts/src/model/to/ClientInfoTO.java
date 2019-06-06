package model.to;

import java.sql.Date;

public class ClientInfoTO {
    private int clientid;
    private String name;
    private String address;
    private float totalincome;
    private float tax;
    private float refund;
    private float tds;
    private String acknowledgement;
    private String remarks;
    private String pan;
    private Date dob;
    private Date entrydate;

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }
    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(float totalincome) {
        this.totalincome = totalincome;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getRefund() {
        return refund;
    }

    public void setRefund(float refund) {
        this.refund = refund;
    }

    public float getTds() {
        return tds;
    }

    public void setTds(float tds) {
        this.tds = tds;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
