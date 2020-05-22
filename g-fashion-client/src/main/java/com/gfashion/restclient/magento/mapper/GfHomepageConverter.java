package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.restclient.magento.homepage.MagentoCategories;
import com.gfashion.restclient.magento.homepage.MagentoCategory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GfHomepageConverter {
    public List<HomepageBrand> convertCategoriesToHomepageBrands(MagentoCategories magentoCategories){
        if(magentoCategories == null){
            return null;
        }

        List<HomepageBrand> homepageBrands = new ArrayList<>();
        for(MagentoCategory magentoCategory : magentoCategories.getItems()){
            homepageBrands.add(convertCategoryToHomepageBrand(magentoCategory));
        }

        return homepageBrands;
    }

    public HomepageBrand convertCategoryToHomepageBrand(MagentoCategory magentoCategory){
        if(magentoCategory == null){
            return null;
        }
        HomepageBrand homepageBrand = new HomepageBrand();
        homepageBrand.setId(magentoCategory.getId());
        homepageBrand.setName(magentoCategory.getName());
        homepageBrand.setLink(getCustomerAttributeValue(magentoCategory, "url_path"));
        homepageBrand.setPhotoUrl(getCustomerAttributeValue(magentoCategory, "url_path"));

        return homepageBrand;
    }

    public List<HomepageDesigner> convertCategoriesToHomepageDesigners(MagentoCategories magentoCategories){
        if(magentoCategories == null){
            return null;
        }

        List<HomepageDesigner> homepageDesigners = new ArrayList<>();
        for(MagentoCategory magentoCategory : magentoCategories.getItems()){
            homepageDesigners.add(convertCategoryToHomepageDesigner(magentoCategory));
        }

        return homepageDesigners;
    }

    public HomepageDesigner convertCategoryToHomepageDesigner(MagentoCategory magentoCategory){
        if(magentoCategory == null){
            return null;
        }
        HomepageDesigner homepageDesigner = new HomepageDesigner();
        homepageDesigner.setId(magentoCategory.getId());
        homepageDesigner.setName(magentoCategory.getName());
        homepageDesigner.setLink(getCustomerAttributeValue(magentoCategory, "url_path"));
        homepageDesigner.setPhotoUrl(getCustomerAttributeValue(magentoCategory, "url_path"));
        return homepageDesigner;
    }

    private String getCustomerAttributeValue(MagentoCategory magentoCategory, String attributeName){
        if (magentoCategory == null || magentoCategory.getCustom_attributes() == null){
            return null;
        }
        List<String> res = magentoCategory.getCustom_attributes().stream().
                filter(attribute -> attribute.get("attribute_code").equalsIgnoreCase(attributeName)).
                map(p -> p.get("value")).collect(Collectors.toList());
        return res != null && res.size() != 0 ? res.get(0) : null;
    }
}
