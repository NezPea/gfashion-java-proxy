package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItemProductOptionExtensionAttributes {

    @SerializedName("custom_options")
    private List<MagentoCartCustomOption> customOptions;
    @SerializedName("bundle_options")
    private List<MagentoCartBundleOption> bundleOptions;
    @SerializedName("configurable_item_options")
    private List<MagentoCartConfigurableItemOption> configurableItemOptions;
    @SerializedName("downloadable_option")
    private MagentoCartDownloadableOption downloadableOption;
    @SerializedName("giftcard_item_option")
    private MagentoCartGiftcardItemOption giftCardItemOption;
}
