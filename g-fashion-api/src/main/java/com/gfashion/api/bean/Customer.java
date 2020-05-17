package com.gfashion.api.bean;

public class Customer {

    private String group_id;
    private String email;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "group_id='" + group_id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
