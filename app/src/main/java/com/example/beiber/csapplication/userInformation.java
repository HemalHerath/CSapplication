package com.example.beiber.csapplication;

public class userInformation {

    String firstName;
    String address;
    String tel;
    String accNo;
    String cardNo;
    String branch;

    public userInformation(){
    }

    public userInformation( String firstName, String address, String tel, String accNo, String cardNo, String branch) {
        this.firstName = firstName;
        this.address = address;
        this.tel = tel;
        this.accNo = accNo;
        this.cardNo = cardNo;
        this.branch = branch;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getAddress() {
        return address;
    }
    public String getTel() {
        return tel;
    }
    public String getAccNo() {
        return accNo;
    }
    public String getBranch() {
        return branch;
    }
    public String getCardNo() {
        return cardNo;
    }

}

