package com.example.quiz;

import com.google.firebase.firestore.ServerTimestamp;

import java.security.PrivateKey;
import java.util.Date;

public class WithdrawRequest {
    private String usingId;
    private String emailAddress;
    private String requestedBy;

    public WithdrawRequest(){

    }

    public WithdrawRequest(String usingId, String emailAddress, String requestedBy) {
        this.usingId = usingId;
        this.emailAddress = emailAddress;
        this.requestedBy = requestedBy;
    }
    public String getUsingId() {
        return usingId;
    }

    public void setUsingId(String usingId) {
        this.usingId = usingId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    @ServerTimestamp
    private Date createAt;


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
