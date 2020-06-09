package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartFileInfo {

    @SerializedName("base64_encoded_data")
    private String base64EncodedData;
    private String type;
    private String name;
}
