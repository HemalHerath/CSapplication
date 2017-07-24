package com.example.beiber.csapplication;

/**
 * Created by BEIBER on 7/15/2017.
 */

public class ProUpload {

    public String name;
    public String address;
    public String tel;
    public String url;

    public ProUpload(){}

    public ProUpload( String name, String address, String tel ,String url) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTel() {
        return tel;
    }
}