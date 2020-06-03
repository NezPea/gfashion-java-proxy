package com.gfashion.restclient;

import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.homepage.HomepageProduct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Temporary Magento Client for Mocking Data for UI
@Component
public class MagentoMockDataClient {
    public CustomizedHomepage getCustomizedHomepage(Integer customerId) {

        // get the default CustomerHomePage
        return getDefaultCustomizedHomepage();

    }

    public CustomizedHomepage getDefaultCustomizedHomepage() {

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
        homepageProducts.add(HomepageProduct.builder().id("1").name("product1").photoUrl("http://image1.gf.com").isFeatured(true).build());
        homepageProducts.add(HomepageProduct.builder().id("2").name("product2").photoUrl("http://image2.gf.com").isFeatured(true).build());
        homepageProducts.add(HomepageProduct.builder().id("3").name("product3").photoUrl("http://image3.gf.com").isFeatured(false).build());
        homepageProducts.add(HomepageProduct.builder().id("4").name("product4").photoUrl("http://image4.gf.com").isFeatured(false).build());
        homepageProducts.add(HomepageProduct.builder().id("5").name("product5").photoUrl("http://image5.gf.com").isFeatured(false).build());
        homepageProducts.add(HomepageProduct.builder().id("6").name("product6").photoUrl("http://image6.gf.com").isFeatured(false).build());
        return homepageProducts;
    }

    private List<HomepageBrand> getDefaultHomepageBrands() {
        List<HomepageBrand> homepageBrands = new ArrayList<>();
        homepageBrands.add(HomepageBrand.builder().id("1").name("Barmain").country("France").photoUrl("http://image1.gf.com").link("http://gucci.com").build());
        homepageBrands.add(HomepageBrand.builder().id("2").name("HERMES").country("France").photoUrl("http://image2.gf.com").link("http://hermes.com").build());
        homepageBrands.add(HomepageBrand.builder().id("3").name("CHANEL").country("France").photoUrl("http://image3.gf.com").link("http://lv.com").build());
        homepageBrands.add(HomepageBrand.builder().id("4").name("CHAUMET").country("France").photoUrl("http://image4.gf.com").link("http://armani.com").build());
        homepageBrands.add(HomepageBrand.builder().id("5").name("Brioni").country("Italy").photoUrl("http://image5.gf.com").link("http://armani.com").build());
        return homepageBrands;
    }

    private List<HomepageDesigner> getDefaultHomepageDesigners() {
        List<HomepageDesigner> homepageDesigners = new ArrayList<>();
        homepageDesigners.add(HomepageDesigner.builder().id("1").name("Miles").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("HUAXIA").photoUrl("../../../assets/images/designer1.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("2").name("Snow").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("HUAXIA").photoUrl("../../../assets/images/designer2.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("3").name("Trump").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("../../../assets/images/designer3.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("4").name("Banon").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("../../../assets/images/designer4.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("5").name("Pompeo").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("../../../assets/images/designer5.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("6").name("Luther").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("HUAXIA").photoUrl("../../../assets/images/designer6.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("7").name("Pence").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("../../../assets/images/designer1.png").build());
        homepageDesigners.add(HomepageDesigner.builder().id("8").name("Ivanka").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("../../../assets/images/designer2.png").build());
        return homepageDesigners;
    }

    private List<HomepageBrand> getDefaultFollowingBrands() {
        List<HomepageBrand> homepageBrands = new ArrayList<>();
        homepageBrands.add(HomepageBrand.builder().id("1").name("Gucci").country("France").photoUrl("http://image1.gf.com").link("http://gucci.com").build());
        return homepageBrands;
    }

    private List<HomepageDesigner> getDefaultFollowingDesigners() {
        List<HomepageDesigner> homepageDesigners = new ArrayList<>();
        homepageDesigners.add(HomepageDesigner.builder().id("1").name("Miles").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("HUAXIA").photoUrl("http://image6.gf.com").build());
        homepageDesigners.add(HomepageDesigner.builder().id("2").name("Snow").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("HUAXIA").photoUrl("http://image7.gf.com").build());
        homepageDesigners.add(HomepageDesigner.builder().id("3").name("Trump").
                cooperatingBrands(new ArrayList<>(Arrays.asList(new String[]{"BALMAIN"}))).country("USA").photoUrl("http://image8.gf.com").build());
        return homepageDesigners;
    }
}
