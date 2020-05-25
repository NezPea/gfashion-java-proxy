package com.gfashion.restclient;

import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.homepage.HomepageProduct;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import com.gfashion.restclient.magento.homepage.MagentoCategories;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MagentoHomepageClient {

    @Value("${magento.url.listCategories}")
    private String listCategories;

    @Value("${magento.url.parameters.parentIdField}")
    private String parentIdField;

    @Value("${magento.url.parameters.field}")
    private String field;

    @Value("${magento.url.parameters.value}")
    private String value;

    @Value("${magento.url.parameters.recommendedDesignersParentId}")
    private String recommendedDesignersParentId;

    @Value("${magento.url.parameters.recommendedBrandsParentId}")
    private String recommendedBrandsParentId;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public CustomizedHomepage getCustomizedHomepage(Integer customerId) throws CustomerUnknowException {

        // TODO： Get Data From Real Magento API; Create Corresponding Magento Objects; Change the Mapper
        // get the default CustomerHomePage
        return getDefaultCustomizedHomepage();

    }

    public CustomizedHomepage getDefaultCustomizedHomepage() throws CustomerUnknowException {

        // TODO： Get Data From Real Magento API; Create Corresponding Magento Objects; Change the Mapper
        // mock the default CustomerHomePage
        CustomizedHomepage customizedHomepage = new CustomizedHomepage();

        customizedHomepage.setId(-1);
        customizedHomepage.setRecommendedProducts(getDefaultHomepageProducts());
        customizedHomepage.setRecommendedDesigners(getDefaultHomepageDesigners());
        customizedHomepage.setRecommendedBrands(getDefaultHomepageBrands());
        customizedHomepage.setFollowingDesigners(getDefaultFollowingDesigners());
        customizedHomepage.setFollowingBrands(getDefaultFollowingBrands());
        return customizedHomepage;
    }

    private List<HomepageProduct> getDefaultHomepageProducts(){
        List<HomepageProduct> homepageProducts = new ArrayList<HomepageProduct>();
        HomepageProduct homepageProduct1 = new HomepageProduct(1, "shoe",
                "https://www.google.com/aclk?sa=l&ai=DChcSEwiC66Dw58HpAhXkCX0KHXP7AkgYABAHGgJwdg&sig=AOD64_1pSaVMQUmT6DFg7tdDM6UARZbXOw&adurl&ctype=5&ved=2ahUKEwimlZbw58HpAhWD6J4KHVppDTkQvhd6BAgBEF0",
                true);
        HomepageProduct homepageProduct2 = new HomepageProduct(2, "hat",
                "https://www.google.com/aclk?sa=l&ai=DChcSEwiI56WD6MHpAhViGH0KHZkSDzYYABAEGgJwdg&sig=AOD64_15E0SJt1kMywkoQWoxEV0KCrpDwA&adurl&ctype=5&ved=2ahUKEwibs5qD6MHpAhXYgZ4KHTD-AjUQwg96BAgBEFk",
                false);
        homepageProducts.add(homepageProduct1);
        homepageProducts.add(homepageProduct2);
        return homepageProducts;
    }

    private List<HomepageBrand> getDefaultHomepageBrands() throws CustomerUnknowException {

        String getDefaultHomepageBrandsUrl = listCategories + "?" +
                String.join("&", new ArrayList<String>(Arrays.asList(new String[]{field + parentIdField, value + recommendedBrandsParentId})));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(getDefaultHomepageBrandsUrl, String.class, null);
            Gson gson = new Gson();
            return this._mapper.convertMagentoCategoriesToHomeBrands(gson.fromJson(responseEntity.getBody(), MagentoCategories.class).getItems());
        } catch (HttpStatusCodeException e) {
            throw new CustomerUnknowException(e.getMessage());
        }
    }

    private List<HomepageDesigner> getDefaultHomepageDesigners() throws CustomerUnknowException {

        String getDefaultHomepageDesignersUrl = listCategories + "?" +
                String.join("&", new ArrayList<String>(Arrays.asList(new String[]{field + parentIdField, value + recommendedDesignersParentId})));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(getDefaultHomepageDesignersUrl, String.class, null);
            Gson gson = new Gson();
            return this._mapper.convertMagentoCategoriesToHomeDesigners(gson.fromJson(responseEntity.getBody(), MagentoCategories.class).getItems());
        } catch (HttpStatusCodeException e) {
            throw new CustomerUnknowException(e.getMessage());
        }
    }

    private List<HomepageBrand> getDefaultFollowingBrands() throws CustomerUnknowException {
        return getDefaultHomepageBrands();
    }

    private List<HomepageDesigner> getDefaultFollowingDesigners() throws CustomerUnknowException {
        return getDefaultHomepageDesigners();
    }

}
