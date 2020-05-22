package com.gfashion.domain.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfStoreConfig {
    private Integer id;
    private String code;
    private Integer website_id;
    private String locale;
    private String base_currency_codel;
    private String default_display_currency_code;
    private String timezone;
    private String weight_unit;
    private String base_url;
    private String base_link_url;
    private String base_static_url;
    private String base_media_url;
    private String secure_base_url;
    private String secure_base_link_url;
    private String secure_base_static_url;
    private String secure_base_media_url;
}
