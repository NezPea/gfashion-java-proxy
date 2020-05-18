package com.gfashion.domain;

public class UserGroup {
    private String code;
    private String tax_class_name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTax_class_name() {
        return tax_class_name;
    }

    public void setTax_class_name(String tax_class_name) {
        this.tax_class_name = tax_class_name;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "code='" + code + '\'' +
                ", tax_class_name='" + tax_class_name + '\'' +
                '}';
    }
}
