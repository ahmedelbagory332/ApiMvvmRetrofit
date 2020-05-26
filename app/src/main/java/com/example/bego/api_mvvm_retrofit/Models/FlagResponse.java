package com.example.bego.api_mvvm_retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// this class tell me that the flag value updated or not

public class FlagResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}