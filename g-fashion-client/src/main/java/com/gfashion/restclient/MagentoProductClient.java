package com.gfashion.restclient;

import com.gfashion.domain.product.*;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.product.MagentoEvaAttribute;
import com.gfashion.restclient.magento.product.MagentoProduct;
import com.gfashion.restclient.magento.product.MagentoProductSearchResponse;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;


@Component
@Slf4j
public class MagentoProductClient {

    @Value("${magento.url.products}")
    private String productsUrl;

    @Autowired
    private RestClient magentoRestClient;


    private final GfMagentoConverter gfMagentoConverter = Mappers.getMapper(GfMagentoConverter.class);

    public Map<String, Map<String, String>> getAttributesOption() {

        String attributesUrl = productsUrl + "attributes?searchCriteria[filterGroups][0][filters][0][field]=is_filterable&searchCriteria[filterGroups][0][filters][0][value]=false&searchCriteria[filterGroups][0][filters][0][conditionType]=neq";
        log.info("attributesUrl:" + attributesUrl);
        ResponseEntity<String> responseEntityAttribute = magentoRestClient.exchangeGet(attributesUrl, String.class, null);

        Gson gson = new Gson();
        GfEvaAttribute gfEvaAttribute = gfMagentoConverter.convertMagentoEvaAttributeToGfEvaAttribute(gson.fromJson(responseEntityAttribute.getBody(), MagentoEvaAttribute.class));

        Map<String, Map<String, String>> attributeOption = new HashMap<>();
        if (gfEvaAttribute.getItems() != null) {
            log.info("size:" + gfEvaAttribute.getItems().size());
            gfEvaAttribute.getItems().forEach(item -> {
                Map<String, String> optionMap = new HashMap<>();
                String attributeCode = item.getAttribute_code();
                item.getOptions().forEach(option -> {
                    optionMap.put(option.get("value"), option.get("label"));
                });

                attributeOption.put(attributeCode, optionMap);
            });

            log.info("attributeOption:" + attributeOption);
        }
        return attributeOption;
    }


    public GfProduct getProductBySku(String sku) throws ProductNotFoundException, ProductUnknowException {
        String getProductUrl = productsUrl + sku;

        try {
            ResponseEntity<String> responseEntityProduct = magentoRestClient.exchangeGet(getProductUrl, String.class, null);
            Gson gson = new Gson();
            return gfMagentoConverter.convertMagentoProductToGfProduct(gson.fromJson(responseEntityProduct.getBody(), MagentoProduct.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(e.getMessage());
            }
            throw new ProductUnknowException(e.getMessage());
        }

    }

    public GfProductSearchResponseFix searchProducts(String query) throws ProductNotFoundException, ProductUnknowException {
        String getProductSearchUrl = productsUrl + query;

        try {
            Map<String, GfAvilableFlter> filters = new HashMap<>();
            Map<String, Map<String, String>> attributesOption = getAttributesOption();

            ResponseEntity<String> responseProductSearch = magentoRestClient.exchangeGet(getProductSearchUrl, String.class, null);

            Gson gson = new Gson();
            GfProductSearchResponse gfProductSearchResponse = gfMagentoConverter.convertMagentoProductSearchToGfProductSearch(gson.fromJson(responseProductSearch.getBody(), MagentoProductSearchResponse.class));

            List<GfChannelProduct> gfChannelProductList = new ArrayList<>();
            GfProductSearchResponseFix gfProductSearchResponseFix = new GfProductSearchResponseFix();

            gfProductSearchResponse.getItems().forEach(gfProduct -> {
                GfChannelProduct gfChannelProduct = new GfChannelProduct();
                gfChannelProduct.setId(gfProduct.getId());
                gfChannelProduct.setSku(gfProduct.getSku());
                gfChannelProduct.setName(gfProduct.getName());
                gfChannelProduct.setBrand_link(gfProduct.getBrand_link());
                gfChannelProduct.setBrand_name(gfProduct.getBrand_name());
                gfChannelProduct.setDesigner_link(gfProduct.getDesigner_link());
                gfChannelProduct.setPrice(gfProduct.getPrice());
                List<GfMediaGalleryEntry> gfMediaGalleryEntry = gfProduct.getMedia_gallery_entries();
                gfMediaGalleryEntry.get(0).getFile();
                gfChannelProduct.setFile(gfMediaGalleryEntry.get(0).getFile());
                gfChannelProductList.add(gfChannelProduct);
                gfProduct.getCustom_attributes().forEach(gfProductCustomAttribute -> {
                    String customAttribute = gfProductCustomAttribute.getAttribute_code();
                    Object customValue = gfProductCustomAttribute.getValue();
                    if (attributesOption.containsKey(customAttribute))
                        if (filters.containsKey(customAttribute)) {

                            GfAvilableFlter gfAvilableFlter = filters.get(customAttribute);
                            List<GfAttributeOption> gfAttributeOptions = gfAvilableFlter.getOptions();
                            Map<String, GfAttributeOption> gfAttributeOptionMap = new HashMap<>();
                            gfAttributeOptions.forEach(gfAttributeOption -> {
                                gfAttributeOptionMap.put(gfAttributeOption.getId(), gfAttributeOption);
                            });
                            if (!gfAttributeOptionMap.containsKey(customValue.toString()) && attributesOption.get(customAttribute).containsKey(customValue.toString())) {

                                GfAttributeOption gfAttributeOption = new GfAttributeOption();
                                gfAttributeOption.setId(customValue.toString());
                                gfAttributeOption.setName(attributesOption.get(customAttribute).get(customValue.toString()));
                                gfAttributeOption.setIsChecked("false");
                                gfAttributeOptions.add(gfAttributeOption);
                                gfAvilableFlter.setOptions(gfAttributeOptions);
                                filters.put(customAttribute, gfAvilableFlter);

                            }
                        } else if (attributesOption.get(customAttribute).containsKey(customValue.toString())) {

                            List<GfAttributeOption> gfAttributeOptions = new ArrayList<>();
                            GfAttributeOption gfAttributeOption = new GfAttributeOption();
                            GfAvilableFlter gfAvilableFlter = new GfAvilableFlter();
                            gfAvilableFlter.setCode(customAttribute);
                            String toFirstUpperCase = customAttribute.substring(0, 1).toUpperCase();
                            String nameCapitalized = toFirstUpperCase + customAttribute.substring(1);
                            gfAvilableFlter.setName(nameCapitalized);
                            gfAttributeOption.setId(customValue.toString());
                            gfAttributeOption.setName(attributesOption.get(customAttribute).get(customValue.toString()));
                            gfAttributeOption.setIsChecked("false");
                            gfAttributeOptions.add(gfAttributeOption);
                            gfAvilableFlter.setOptions(gfAttributeOptions);
                            filters.put(customAttribute, gfAvilableFlter);
                        }
                });
            });
            gfProductSearchResponse.getSearch_criteria().getFilter_groups().forEach(Filter_group -> {
                Filter_group.getFilters().forEach(searchFilter -> {
                    if (filters.containsKey(searchFilter.getField())) {

                        GfAvilableFlter gfAvilableFlter = filters.get(searchFilter.getField());
                        List<GfAttributeOption> gfAttributeOptions = gfAvilableFlter.getOptions();
                        List<GfAttributeOption> gfAttributeOptionTmp = new ArrayList<>();
                        Map<String, GfAttributeOption> gfAttributeOptionMap = new HashMap<>();
                        gfAttributeOptions.forEach(gfAttributeOption -> {
                            gfAttributeOptionMap.put(gfAttributeOption.getId(), gfAttributeOption);
                        });

                        GfAttributeOption gfAttributeOption = gfAttributeOptionMap.get(searchFilter.getValue());
                        gfAttributeOption.setIsChecked("true");
                        gfAttributeOptionTmp.add(gfAttributeOption);
                        gfAvilableFlter.setOptions(gfAttributeOptionTmp);
                        filters.put(searchFilter.getField(), gfAvilableFlter);
                    }
                });
            });
            List<GfAvilableFlter> gfAvilableFlters = new ArrayList<>();
            filters.forEach((key, values) -> {
                gfAvilableFlters.add(values);
            });
            gfProductSearchResponseFix.setItems(gfChannelProductList);
            gfProductSearchResponseFix.setSearch_criteria(gfProductSearchResponse.getSearch_criteria());
            gfProductSearchResponseFix.setTotal_count(gfProductSearchResponse.getTotal_count());
            gfProductSearchResponseFix.setAvavilable_filters(gfAvilableFlters);
            return gfProductSearchResponseFix;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(e.getMessage());
            }
            throw new ProductUnknowException(e.getMessage());
        }

    }
}

