package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAddressExtensionAttributes {

    private List<MagentoCartDiscount> discounts;
    @SerializedName("gift_registry_id")
    private Integer giftRegistryId;
}
