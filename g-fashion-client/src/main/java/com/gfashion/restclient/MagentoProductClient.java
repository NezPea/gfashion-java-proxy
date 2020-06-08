package com.gfashion.restclient;

import com.gfashion.domain.product.*;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.product.*;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;


@Component
@Slf4j
public class MagentoProductClient {

    @Value("${magento.url.products}")
    private String productsUrl;

    @Value("${magento.url.categories}")
    private String categoriesUrl;

    @Value("${magento.url.stockItems}")
    private String stockItems;

    @Value("${magento.url.designersParentId}")
    private String designersParentId;

    @Value("${magento.url.brandsParentId}")
    private String brandsParentId;


    @Value("${magento.url.locale.en}")
    private String localeEn;

    @Value("${magento.url.locale.zh}")
    private String localeZh;



    @Autowired
    private RestClient magentoRestClient;

    @Autowired
    private MagentoStoreClient magentoStoreClient;
    private String secureBaseMediaUrl = ""; // 产品图片地址

    private final GfMagentoConverter gfMagentoConverter = Mappers.getMapper(GfMagentoConverter.class);

    /**
     * 加入参数extraHeaders，如果已经拿到token，避免再去取token
     *
     * @param extraHeaders
     * @return
     */
    public Map<String, Map<String, String>> getAttributesOption(MultiValueMap<String, String> extraHeaders) {

        String attributesUrl = productsUrl + "attributes?searchCriteria[filterGroups][0][filters][0][field]=is_filterable&searchCriteria[filterGroups][0][filters][0][value]=false&searchCriteria[filterGroups][0][filters][0][conditionType]=neq";
        log.info("attributesUrl:" + attributesUrl);
        ResponseEntity<String> responseEntityAttribute = magentoRestClient.exchangeGet(attributesUrl, String.class, extraHeaders);

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

    public GfProductCategory getCategoryById(Integer CategoryId, MultiValueMap<String, String> extraHeaders) {
        String getProductUrl = categoriesUrl + CategoryId;

        ResponseEntity<String> responseEntityCategory = magentoRestClient.exchangeGet(getProductUrl, String.class, extraHeaders);
        Gson gson = new Gson();
        return gfMagentoConverter.convertMagentoProductCategoryToGfProductCategory(gson.fromJson(responseEntityCategory.getBody(), MagentoProductCategory.class));
    }

    public GfStockItem getStockItemBySku(String productSku, MultiValueMap<String, String> extraHeaders) {
        String getProductUrl = stockItems + productSku;

        ResponseEntity<String> responseEntityStockItem = magentoRestClient.exchangeGet(getProductUrl, String.class, extraHeaders);
        Gson gson = new Gson();
        return gfMagentoConverter.convertMagentoStockItemToGfStockItem(gson.fromJson(responseEntityStockItem.getBody(), MagentoStockItem.class));
    }

    /**
     * getProductBySku
     *
     * @param sku
     * @return
     * @throws ProductNotFoundException
     * @throws ProductUnknowException
     */
    public GfProduct getProductBySku(String sku) throws ProductNotFoundException, ProductUnknowException {
        String getProductUrl = productsUrl + sku;
        String productFilePath = "catalog/product";
        try {
            // 获取图片的基础地址
            GfStoreConfig[] gfStoreConfig = magentoStoreClient.getStoreConfig();
            if (null != gfStoreConfig && gfStoreConfig.length > 0) {
                secureBaseMediaUrl = gfStoreConfig[0].getSecure_base_media_url() + productFilePath;
            } else {
                secureBaseMediaUrl = "https://www.gfashion2020.tk/media/" + productFilePath;
            }
            HttpHeaders headers = magentoRestClient.getHeaders(null);
            ResponseEntity<String> responseEntityProduct = magentoRestClient.exchangeGet(getProductUrl, String.class, headers);
            Gson gson = new Gson();
            GfProduct gfProduct = gfMagentoConverter.convertMagentoProductToGfProduct(gson.fromJson(responseEntityProduct.getBody(), MagentoProduct.class));
            List<GfMediaGalleryEntry> gfMediaGalleryEntryList = gfProduct.getMedia_gallery_entries();
            if (gfMediaGalleryEntryList.size() > 0) {
                gfMediaGalleryEntryList.forEach(gfMediaGalleryEntry -> {
                    String file = "";
                    file = secureBaseMediaUrl + gfMediaGalleryEntry.getFile();
                    gfMediaGalleryEntry.setFile(file);
                });
            }
            List<GfProductCustomAttribute> gfProductCustomAttributeList = gfProduct.getCustom_attributes();
            Map<String, Map<String, String>> attributesOption = getAttributesOption(headers);
            Map<String, GfAvilableFlter> filters = new HashMap<>();
            if (null != gfProductCustomAttributeList && gfProductCustomAttributeList.size() > 0) {

                gfProductCustomAttributeList.forEach(gfProductCustomAttribute -> {
                    String customAttribute = gfProductCustomAttribute.getAttribute_code();
                    Object customValue = gfProductCustomAttribute.getValue();
                    if (customValue instanceof String) {
                        if (customValue.toString().contains(",")) {
                            // 多个id 例如：
                            // {
                            //      "attribute_code": "activity",
                            //      "value": "5435,5436,5444,5438"
                            //    },
                        } else {
                            // 单个id
                            if (attributesOption.containsKey(customAttribute)) {
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
                                        gfAttributeOption.setIsChecked("true");
                                        gfAttributeOptions.add(gfAttributeOption);
                                        gfAvilableFlter.setOptions(gfAttributeOptions);
                                        filters.put(customAttribute, gfAvilableFlter);

                                        gfProductCustomAttribute.setValue(gfAttributeOptions);
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
                                    gfAttributeOption.setIsChecked("true");
                                    gfAttributeOptions.add(gfAttributeOption);
                                    gfAvilableFlter.setOptions(gfAttributeOptions);
                                    filters.put(customAttribute, gfAvilableFlter);

                                    gfProductCustomAttribute.setValue(gfAttributeOption);
                                }
                            }
                        }
                    }
                    if (customValue instanceof ArrayList) {
                        // 是一个字符串数组，例如：这种情况
                        // {
                        //      "attribute_code": "category_ids",
                        //      "value": [
                        //        "3",
                        //        "5"
                        //      ]
                        //    },
                        List<GfProductCategory> GfProductCategoryList = new ArrayList<>();
                        if ("category_ids".equals(customAttribute)) {
                            ((ArrayList) customValue).forEach(customValueOne -> {
                                GfProductCategory gfProductCategory = getCategoryById(Integer.parseInt(customValueOne.toString()), headers);
                                if (null != gfProductCategory.getParent_id()) {
                                    // 品牌的父类id是50，设计师的父类id是46
                                    if (gfProductCategory.getParent_id() == Integer.parseInt(designersParentId)) {
                                        Integer designer_link = gfProductCategory.getId();
                                        String designer_name = gfProductCategory.getName();
                                        gfProduct.setDesigner_name(designer_name);
                                        gfProduct.setDesigner_link(designer_link);
                                    } else if (gfProductCategory.getParent_id() == Integer.parseInt(brandsParentId)) {
                                        Integer brand_link = gfProductCategory.getId();
                                        String brand_name = gfProductCategory.getName();
                                        gfProduct.setBrand_link(brand_link);
                                        gfProduct.setBrand_name(brand_name);
                                    }
                                }
                                GfProductCategoryList.add(gfProductCategory);
                            });
                        }
                        gfProductCustomAttribute.setValue(GfProductCategoryList);
                    }

                });
            }

            List<GfProductLink> gfProductLinkList = gfProduct.getProduct_links();
            if (null != gfProductLinkList && gfProductLinkList.size() > 0) {
                // 循环获取关联产品的名称、价格、图片地址
                gfProductLinkList.forEach(GfProductLink -> {
                    ResponseEntity<String> responseEntityProduct1 = magentoRestClient.exchangeGet(productsUrl + GfProductLink.getLinked_product_sku(), String.class, headers);
                    GfProduct gfProduct1 = gfMagentoConverter.convertMagentoProductToGfProduct(gson.fromJson(responseEntityProduct1.getBody(), MagentoProduct.class));

                    GfProductLink.setName(gfProduct1.getName()); // 产品名称
                    GfProductLink.setPrice(gfProduct1.getPrice()); // 产品价格
                    List<GfMediaGalleryEntry> gfMediaGalleryEntryList1 = gfProduct1.getMedia_gallery_entries();
                    String file = "";
                    if (gfMediaGalleryEntryList1.size() > 0) {
                        file = secureBaseMediaUrl + gfMediaGalleryEntryList1.get(0).getFile();
                    }
                    GfProductLink.setFile(file); // 产品图片
                });
            }

            // 获取扩展属性
            GfExtensionAttribute gfExtensionAttribute = gfProduct.getExtension_attributes();
            if (null != gfExtensionAttribute) {
                // 获取产品的库存信息
                GfStockItem gfStockItem = gfExtensionAttribute.getStock_item();
                if (null != gfStockItem) {
                    gfProduct.setPurchase_number_limit(gfStockItem.getQty());
                }

                List<GfConfigurableProductOption> gfConfigurableProductOptionList = gfExtensionAttribute.getConfigurable_product_options();
                if (null != gfConfigurableProductOptionList) {
                    gfConfigurableProductOptionList.forEach(gfConfigurableProductOption -> {
                        List<GfConfigurableProductOptionValue> gfConfigurableProductOptionValueList =
                                gfConfigurableProductOption.getValues();
                        String customAttribute = gfConfigurableProductOption.getLabel().toLowerCase();
                        if (null != gfConfigurableProductOptionValueList) {
                            gfConfigurableProductOptionValueList.forEach(gfConfigurableProductOptionValue -> {
                                Integer attributeId = gfConfigurableProductOptionValue.getValue_index();
                                // 单个id
                                if (attributesOption.containsKey(customAttribute)) {
                                    if (filters.containsKey(customAttribute)) {
                                        GfAvilableFlter gfAvilableFlter = filters.get(customAttribute);
                                        List<GfAttributeOption> gfAttributeOptions = gfAvilableFlter.getOptions();
                                        Map<String, GfAttributeOption> gfAttributeOptionMap = new HashMap<>();
                                        gfAttributeOptions.forEach(gfAttributeOption -> {
                                            gfAttributeOptionMap.put(gfAttributeOption.getId(), gfAttributeOption);
                                        });
                                        if (!gfAttributeOptionMap.containsKey(attributeId.toString()) && attributesOption.get(customAttribute).containsKey(attributeId.toString())) {

                                            GfAttributeOption gfAttributeOption = new GfAttributeOption();
                                            gfAttributeOption.setId(attributeId.toString());
                                            gfAttributeOption.setName(attributesOption.get(customAttribute).get(attributeId.toString()));
                                            gfAttributeOption.setIsChecked("true");
                                            gfAttributeOptions.add(gfAttributeOption);
                                            gfAvilableFlter.setOptions(gfAttributeOptions);
                                            filters.put(customAttribute, gfAvilableFlter);

                                            gfConfigurableProductOptionValue.setValue(gfAttributeOption);
                                        }
                                    } else if (attributesOption.get(customAttribute).containsKey(attributeId.toString())) {
                                        List<GfAttributeOption> gfAttributeOptions = new ArrayList<>();
                                        GfAttributeOption gfAttributeOption = new GfAttributeOption();
                                        GfAvilableFlter gfAvilableFlter = new GfAvilableFlter();
                                        gfAvilableFlter.setCode(customAttribute);
                                        String toFirstUpperCase = customAttribute.substring(0, 1).toUpperCase();
                                        String nameCapitalized = toFirstUpperCase + customAttribute.substring(1);
                                        gfAvilableFlter.setName(nameCapitalized);
                                        gfAttributeOption.setId(attributeId.toString());
                                        gfAttributeOption.setName(attributesOption.get(customAttribute).get(attributeId.toString()));
                                        gfAttributeOption.setIsChecked("true");
                                        gfAttributeOptions.add(gfAttributeOption);
                                        gfAvilableFlter.setOptions(gfAttributeOptions);
                                        filters.put(customAttribute, gfAvilableFlter);

                                        gfConfigurableProductOptionValue.setValue(gfAttributeOption);
                                    }
                                }
                            });
                        }
                    });
                }

            }

            return gfProduct;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(e.getMessage());
            }
            throw new ProductUnknowException(e.getMessage());
        }

    }

    public GfProductSearchResponseFix searchProducts(String query, Integer category_id) throws ProductNotFoundException, ProductUnknowException {
        String getProductSearchUrl = productsUrl + query.replace("localeEn",localeEn).replace("localeZh",localeZh);

        try {

            HttpHeaders headers = magentoRestClient.getHeaders(null);
            Map<String, GfAvilableFlter> filters = new HashMap<>();
            Map<String, Map<String, String>> attributesOption = getAttributesOption(headers);

            GfProductCategory gfProductCategory = getCategoryById(category_id, headers);
            String category_name = gfProductCategory.getName();
            //返回meganto格式产品
            ResponseEntity<String> responseProductSearch = magentoRestClient.exchangeGet(getProductSearchUrl, String.class, headers);

            Gson gson = new Gson();
            //将产品格式转换为GfProductSearchResponse
            GfProductSearchResponse gfProductSearchResponse = gfMagentoConverter.convertMagentoProductSearchToGfProductSearch(gson.fromJson(responseProductSearch.getBody(), MagentoProductSearchResponse.class));

            //产品详情，精简字段
            List<GfChannelProduct> gfChannelProductList = new ArrayList<>();

            //频道页产品详情，精简字段字段，转类型，返回结构数据
            GfProductSearchResponseFix gfProductSearchResponseFix = new GfProductSearchResponseFix();

            gfProductSearchResponse.getItems().forEach(gfProduct -> {
                //产品详情，精简字段设置
                GfChannelProduct gfChannelProduct = new GfChannelProduct();
                gfChannelProduct.setId(gfProduct.getId());
                gfChannelProduct.setSku(gfProduct.getSku());
                gfChannelProduct.setName(gfProduct.getName());
                gfChannelProduct.setType_id(gfProduct.getType_id());
                gfChannelProduct.setBrand_link(gfProduct.getBrand_link());
                gfChannelProduct.setBrand_name(gfProduct.getBrand_name());
                gfChannelProduct.setDesigner_link(gfProduct.getDesigner_link());
                gfChannelProduct.setDesigner_name(gfProduct.getDesigner_name());
                gfChannelProduct.setPrice(gfProduct.getPrice());
                List<GfMediaGalleryEntry> gfMediaGalleryEntry = gfProduct.getMedia_gallery_entries();

                //设置图片地址
                gfChannelProduct.setFile(gfMediaGalleryEntry.get(0).getFile());
                gfChannelProductList.add(gfChannelProduct);
                //设置产品属性
                // gfProductCustomAttribute 例子 getAttribute_code="color" getValue="5487"
                if (null != gfProduct.getCustom_attributes() && gfProduct.getCustom_attributes().size() > 0) {
                    gfProduct.getCustom_attributes().forEach(gfProductCustomAttribute -> {
                        String customAttribute = gfProductCustomAttribute.getAttribute_code();
                        Object customValue = gfProductCustomAttribute.getValue();

                        //在属性map中查找 getAttribute_code="color" ，如果有这个属性，返回颜色码 "5487" 对应的颜色 "Yellow"
                        if (attributesOption.containsKey(customAttribute))
                            /* Map<String, GfAvilableFlter> filters key=color
                            GfAvilableFlter 包含  code; name; List<GfAttributeOption> options;
                            返回 filters 格式 avavilable_filters
                             */
                            //如果包含key，添加options
                            if (filters.containsKey(customAttribute)) {
                                /*
                                "avavilable_filters": [
                                   {
                                        "code": "color",
                                        "name": "Color",
                                        "options": [
                                            {
                                                "id": "5487",
                                                "name": "Yellow",
                                                "isChecked": "true"
                                            }
                                        ]
                                    }
                                ]
                                 */
                                GfAvilableFlter gfAvilableFlter = filters.get(customAttribute);
                                //获取AttributeOption字段列表
                                /* GfAttributeOption 包含 id; name; isChecked;
                                 */
                                List<GfAttributeOption> gfAttributeOptions = gfAvilableFlter.getOptions();
                                Map<String, GfAttributeOption> gfAttributeOptionMap = new HashMap<>();
                                gfAttributeOptions.forEach(gfAttributeOption -> {
                                    gfAttributeOptionMap.put(gfAttributeOption.getId(), gfAttributeOption);
                                });

                                //如果当前颜色代码不存在 5487，但是在属性集合attributesOption.get("color").containsKey("5487") 中有对应的值 Yellow
                                if (!gfAttributeOptionMap.containsKey(customValue.toString()) && attributesOption.get(customAttribute).containsKey(customValue.toString())) {


                                    GfAttributeOption gfAttributeOption = new GfAttributeOption();
                                    //gfAttributeOption 设置id 5487
                                    gfAttributeOption.setId(customValue.toString());
                                    //gfAttributeOption 设置name,查找属性集合attributesOption.get("color").get("5487") 的结果 Yellow
                                    gfAttributeOption.setName(attributesOption.get(customAttribute).get(customValue.toString()));
                                    //gfAttributeOption 这里设置isChecked 为false，后面查找入参时如果找到，则可设为true

                                    gfAttributeOption.setIsChecked("false");
                                    gfAttributeOptions.add(gfAttributeOption);
                                    gfAvilableFlter.setOptions(gfAttributeOptions);
                                    filters.put(customAttribute, gfAvilableFlter);

                                }

                                //如果不包含key，初始化code，name，options，但是在属性集合attributesOption.get("color").containsKey("5487") 中有对应的值 Yellow
                            } else if (attributesOption.get(customAttribute).containsKey(customValue.toString())) {


                                List<GfAttributeOption> gfAttributeOptions = new ArrayList<>();
                                GfAttributeOption gfAttributeOption = new GfAttributeOption();
                                GfAvilableFlter gfAvilableFlter = new GfAvilableFlter();
                                //初始化code
                                gfAvilableFlter.setCode(customAttribute);

                                //初始化name 将code首字母大写
                                String toFirstUpperCase = customAttribute.substring(0, 1).toUpperCase();
                                String nameCapitalized = toFirstUpperCase + customAttribute.substring(1);
                                gfAvilableFlter.setName(nameCapitalized);

                                //初始化options
                                gfAttributeOption.setId(customValue.toString());
                                gfAttributeOption.setName(attributesOption.get(customAttribute).get(customValue.toString()));
                                gfAttributeOption.setIsChecked("false");
                                gfAttributeOptions.add(gfAttributeOption);
                                gfAvilableFlter.setOptions(gfAttributeOptions);
                                filters.put(customAttribute, gfAvilableFlter);
                            }
                    });
                }

            });
            /* 搜索条件示例，对于包含在搜索条件中的属性，颜色码值 5487，5477，5485 设置 gfAttributeOption.setIsChecked("true")
            ?searchCriteria[filter_groups][0][filters][0][field]=category_id&
             searchCriteria[filter_groups][0][filters][0][value]=23&
             searchCriteria[filter_groups][0][filters][0][condition_type]=eq&
             searchCriteria[filter_groups][1][filters][0][field]=price&
             searchCriteria[filter_groups][1][filters][0][value]=150&
             searchCriteria[filter_groups][1][filters][0][condition_type]=lt&
             searchCriteria[filter_groups][2][filters][0][field]=color&
             searchCriteria[filter_groups][2][filters][0][value]=5487&
             searchCriteria[filter_groups][2][filters][0][condition_type]=eq&
             searchCriteria[filter_groups][2][filters][1][field]=color&
             searchCriteria[filter_groups][2][filters][1][value]=5477&
             searchCriteria[filter_groups][2][filters][1][condition_type]=eq&
             searchCriteria[filter_groups][2][filters][2][field]=color&
             searchCriteria[filter_groups][2][filters][2][value]=5485&
             searchCriteria[filter_groups][2][filters][2][condition_type]=eq
             */
            gfProductSearchResponse.getSearch_criteria().getFilter_groups().forEach(Filter_group -> {
                Filter_group.getFilters().forEach(searchFilter -> {
                    //searchFilter.getField=color
                    if (filters.containsKey(searchFilter.getField())) {

                        GfAvilableFlter gfAvilableFlter = filters.get(searchFilter.getField());
                        List<GfAttributeOption> gfAttributeOptions = gfAvilableFlter.getOptions();
                        //可用条件集合，更新setIsChecked
                        List<GfAttributeOption> gfAttributeOptionTmp = new ArrayList<>();
                        Map<String, GfAttributeOption> gfAttributeOptionMap = new HashMap<>();
                        //将GfAttributeOption List 还原成map key=id value=gfAttributeOption<id,name,isChecked>
                        gfAttributeOptions.forEach(gfAttributeOption -> {
                            gfAttributeOptionMap.put(gfAttributeOption.getId(), gfAttributeOption);
                        });
                        gfAttributeOptionMap.forEach((optionKey,optionValue)->{
                            if(optionKey.equals(searchFilter.getValue())){
                                optionValue.setIsChecked("true");
                                gfAttributeOptionTmp.add(optionValue);
                            }else{
                                gfAttributeOptionTmp.add(optionValue);
                            }
                        });
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
            gfProductSearchResponseFix.setCategory_name(category_name);
            gfProductSearchResponseFix.setCategory_id(category_id);
            return gfProductSearchResponseFix;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException(e.getMessage());
            }
            throw new ProductUnknowException(e.getMessage());
        }

    }
}

