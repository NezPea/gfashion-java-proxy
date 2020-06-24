package com.gfashion.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfAdditionalData {
    private String cc_type;
    private String number;
    private String cvc;
    private String expiryMonth;
    private String expiryYear;
    private String holderName;
    private Boolean store_cc;
    private Boolean java_enabled;
    private Integer screen_color_depth;
    private Integer screen_width;
    private Integer screen_height;
    private Integer timezone_offset;
    private String language;
}
