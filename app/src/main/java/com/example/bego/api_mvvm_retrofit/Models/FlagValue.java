package com.example.bego.api_mvvm_retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// this class for get the flag value which tell where i will get data from room database or server

public class FlagValue {


    @SerializedName("flag")
    @Expose
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}