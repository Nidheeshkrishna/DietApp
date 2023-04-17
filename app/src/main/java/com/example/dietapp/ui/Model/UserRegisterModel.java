package com.example.dietapp.ui.Model;

import com.google.gson.annotations.SerializedName;

public class UserRegisterModel {

    @SerializedName("type")
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
