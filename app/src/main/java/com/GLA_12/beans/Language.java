package com.GLA_12.beans;

public class Language {
    private String english_name;
    private String iso_639_1;
    private String name;

    public String getName() {
        return name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

}