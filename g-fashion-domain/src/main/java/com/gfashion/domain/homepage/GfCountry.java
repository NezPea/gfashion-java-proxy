package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCountry {
    private String id;
    private String twoLetterAbbreviation;
    private String threeLetterAbbreviation;
    private String fullNameLocale;
    private String fullNameEnglish;
    private List<GfRegion> availableRegions;
}
