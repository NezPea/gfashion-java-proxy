package com.gfashion.restclient.magento.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAddressExtensionAttributes {

    private List<MagentoCartDiscount> discounts;
    @SerializedName("gift_registry_id")
    private Integer giftRegistryId;
}
