package com.gfashion.domain.gfproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfashion.domain.product.GfExtensionAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductShort {
    private Integer id;
    private String skuId;
    private String brand;
    private String name;
    private Integer categoryId;
    private String price;
    private String gclubMemberPrice;
    private String discountPrice;
    private String image;
}
