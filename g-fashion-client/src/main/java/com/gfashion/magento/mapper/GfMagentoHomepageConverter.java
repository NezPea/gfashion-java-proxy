package com.gfashion.magento.mapper;

import com.gfashion.domain.homepage.*;
import com.gfashion.magento.entity.homepage.MagentoCategory;
import com.gfashion.magento.entity.homepage.MagentoCountry;
import com.gfashion.magento.entity.homepage.MagentoRegion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface GfMagentoHomepageConverter {
    List<HomepageBrand> convertMagentoCategoriesToHomeBrands(List<MagentoCategory> magentoCategories);

    @Mapping(source = "custom_attributes", target = "link", qualifiedByName = "getLinkFromAttribute")
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageBrand convertMagentoCategoryToHomeBrand(MagentoCategory magentoCategory);

    List<HomepageDesigner> convertMagentoCategoriesToHomeDesigners(List<MagentoCategory> magentoCategories);

    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageDesigner convertMagentoCategoryToHomeDesigner(MagentoCategory magentoCategory);

    @Named("getPhotoUrlFromAttribute")
    static String getPhotoUrlFromAttribute(List<Map<String, String>> customerAttributes) {
        return getCustomerAttributeValue(customerAttributes, "url_path");
    }

    static String getCustomerAttributeValue(List<Map<String, String>> customerAttributes, String attributeName) {
        if (customerAttributes == null) {
            return null;
        }
        List<String> res = customerAttributes.stream().
                filter(attribute -> attribute.get("attribute_code").equalsIgnoreCase(attributeName)).
                map(p -> p.get("value")).collect(Collectors.toList());
        return res != null && res.size() != 0 ? res.get(0) : null;
    }

    List<GfCategory> convertMagentoCategoriesToGfCategories(List<MagentoCategory> magentoCategories);

    GfCategory convertMagentoCategoryToGfCategory(MagentoCategory magentoCategory);

    List<GfCountry> convertMagentoCountriesToGfCountries(MagentoCountry[] magentoCountries);

    @Mapping(source = "two_letter_abbreviation", target = "twoLetterAbbreviation")
    @Mapping(source = "three_letter_abbreviation", target = "threeLetterAbbreviation")
    @Mapping(source = "full_name_locale", target = "fullNameLocale")
    @Mapping(source = "full_name_english", target = "fullNameEnglish")
    @Mapping(source = "available_regions", target = "availableRegions")
    GfCountry convertMagentoCountryToGfCountry(MagentoCountry magentoCountry);

    GfRegion convertMagentoRegionToGfRegion(MagentoRegion magentoRegion);
}
