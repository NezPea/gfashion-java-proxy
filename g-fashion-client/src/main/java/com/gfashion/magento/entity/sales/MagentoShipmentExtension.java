package com.gfashion.magento.entity.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentExtension implements Serializable {
    @SerializedName("source_code")
    private String sourceCode;
}
