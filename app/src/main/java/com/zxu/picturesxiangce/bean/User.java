package com.zxu.picturesxiangce.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String tel;
    private String gender;
    private String declaration;
    private String back_img_url;
    private String pass_word;

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getBack_img_url() {
        return back_img_url;
    }

    public void setBack_img_url(String back_img_url) {
        this.back_img_url = back_img_url;
    }
}
