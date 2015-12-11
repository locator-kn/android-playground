package com.locator_app.lomboksample;

import lombok.Data;

public @Data class MyClass {

    private String someMember;

    public void setSomeMember(String s) {
        someMember = s + " - SCHÖN HIER!";
    }

    public String getSomeMember() {
        return "'" + someMember + "'";
    }

}
