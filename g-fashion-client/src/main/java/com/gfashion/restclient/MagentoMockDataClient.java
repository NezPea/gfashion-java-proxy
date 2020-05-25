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
        HomepageProduct homepageProduct1 = new HomepageProduct(1, "product1",
                "http://image1.gf.com", true);
        HomepageProduct homepageProduct2 = new HomepageProduct(2, "product2",
                "http://image2.gf.com", false);
        HomepageProduct homepageProduct3 = new HomepageProduct(3, "product3",
                "http://image3.gf.com", false);
        HomepageProduct homepageProduct4 = new HomepageProduct(4, "product4",
                "http://image4.gf.com", false);
        HomepageProduct homepageProduct5 = new HomepageProduct(5, "product5",
                "http://image5.gf.com", false);
        homepageProducts.add(homepageProduct1);
        homepageProducts.add(homepageProduct2);
        homepageProducts.add(homepageProduct3);
        homepageProducts.add(homepageProduct4);
        homepageProducts.add(homepageProduct5);
        return homepageProducts;
    }

    private List<HomepageBrand> getDefaultHomepageBrands() {
        List<HomepageBrand> homepageBrands = new ArrayList<>();
        HomepageBrand homepageBrand1 = new HomepageBrand(1, "Barmain", "France", "http://image1.gf.com", "http://gucci.com");
        HomepageBrand homepageBrand2 = new HomepageBrand(2, "HERMES", "France", "http://image2.gf.com", "http://hermes.com");
        HomepageBrand homepageBrand3 = new HomepageBrand(3, "CHANEL", "France", "http://image3.gf.com", "http://lv.com");
        HomepageBrand homepageBrand4 = new HomepageBrand(4, "CHAUMET", "France", "http://image4.gf.com", "http://armani.com");
        HomepageBrand homepageBrand5 = new HomepageBrand(5, "Brioni", "Italy", "http://image4.gf.com", "http://armani.com");

        homepageBrands.add(homepageBrand1);
        homepageBrands.add(homepageBrand2);
        homepageBrands.add(homepageBrand3);
        homepageBrands.add(homepageBrand4);
        homepageBrands.add(homepageBrand5);
        return homepageBrands;
    }

    private List<HomepageDesigner> getDefaultHomepageDesigners() {
        List<HomepageDesigner> homepageDesigners = new ArrayList<>();
        HomepageDesigner homepageDesigner1 = new HomepageDesigner(1, "Miles", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "HUAXIA", "../../../assets/images/designer1.png");
        HomepageDesigner homepageDesigner2 = new HomepageDesigner(2, "Snow", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "HUAXIA", "../../../assets/images/designer2.png");
        HomepageDesigner homepageDesigner3 = new HomepageDesigner(3, "Trump", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "../../../assets/images/designer3.png");
        HomepageDesigner homepageDesigner4 = new HomepageDesigner(4, "Banon", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "../../../assets/images/designer4.png");
        HomepageDesigner homepageDesigner5 = new HomepageDesigner(5, "Pompeo", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "../../../assets/images/designer5.png");
        HomepageDesigner homepageDesigner6 = new HomepageDesigner(6, "Luther", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "HUAXIA", "../../../assets/images/designer6.png");
        HomepageDesigner homepageDesigner7 = new HomepageDesigner(7, "Pence", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "../../../assets/images/designer1.png");
        HomepageDesigner homepageDesigner8 = new HomepageDesigner(8, "Ivanka", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "../../../assets/images/designer2.png");

        homepageDesigners.add(homepageDesigner1);
        homepageDesigners.add(homepageDesigner2);
        homepageDesigners.add(homepageDesigner3);
        homepageDesigners.add(homepageDesigner4);
        homepageDesigners.add(homepageDesigner5);
        homepageDesigners.add(homepageDesigner6);
        homepageDesigners.add(homepageDesigner7);
        homepageDesigners.add(homepageDesigner8);
        return homepageDesigners;
    }

    private List<HomepageBrand> getDefaultFollowingBrands() {
        List<HomepageBrand> homepageBrands = new ArrayList<>();
        HomepageBrand homepageBrand1 = new HomepageBrand(1, "Gucci", "France", "http://image1.gf.com", "http://gucci.com");

        homepageBrands.add(homepageBrand1);
        return homepageBrands;
    }

    private List<HomepageDesigner> getDefaultFollowingDesigners() {
        List<HomepageDesigner> homepageDesigners = new ArrayList<>();
        HomepageDesigner homepageDesigner1 = new HomepageDesigner(1, "Miles", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "HUAXIA", "http://image6.gf.com");
        HomepageDesigner homepageDesigner2 = new HomepageDesigner(2, "Snow", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "HUAXIA", "http://image7.gf.com");
        HomepageDesigner homepageDesigner3 = new HomepageDesigner(3, "Trump", new ArrayList<String>(Arrays.asList(new String[]{"BALMAIN"})), "USA", "http://image8.gf.com");

        homepageDesigners.add(homepageDesigner1);
        homepageDesigners.add(homepageDesigner2);
        homepageDesigners.add(homepageDesigner3);
        return homepageDesigners;
    }
}
