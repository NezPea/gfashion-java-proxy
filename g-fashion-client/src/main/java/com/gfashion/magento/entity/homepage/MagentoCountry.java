package com.gfashion.magento.entity.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCountry {
    private String id;
    private String two_letter_abbreviation;
    private String three_letter_abbreviation;
    private String full_name_locale;
    private String full_name_english;
    private List<MagentoRegion> available_regions;
}