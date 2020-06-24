package com.gfashion.domain.elasticsearch;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class GfProduct {
    private String id;
    private String name;
    private Integer price;
    private Integer discountPrice;
    private String currency;
    private String des;
    private String smallPic;
    private Long designerId;
    private Long categoryId;
    private String designerName;
    private Integer sale;
    private String[] sizeList;

    public BigDecimal getPrice() {
        return centToDollar(price);
    }

    public BigDecimal getDiscountPrice() {
        return centToDollar(discountPrice);
    }

    private BigDecimal centToDollar(Integer num) {
        if (num == null) {
            return null;
        }
        return BigDecimal.valueOf(num).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
