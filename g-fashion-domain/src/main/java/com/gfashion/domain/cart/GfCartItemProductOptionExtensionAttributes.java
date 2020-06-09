package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartItemProductOptionExtensionAttributes {

    private List<GfCartCustomOption> customOptions;
    private List<GfCartBundleOption> bundleOptions;
    private List<GfCartConfigurableItemOption> configurableItemOptions;
    private GfCartDownloadableOption downloadableOption;
    private GfCartGiftcardItemOption giftCardItemOption;
}
