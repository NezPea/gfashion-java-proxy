package com.gfashion.domain;

public class UserGroup {

    //用户组 内部代码
    private String code;

    //用户组折扣信息
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
