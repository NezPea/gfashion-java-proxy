package com.gfashion.magento.client;

import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.domain.homepage.GfCountry;
import com.gfashion.magento.entity.homepage.MagentoCategories;
import com.gfashion.magento.exception.HomepageException;
import com.gfashion.magento.entity.homepage.MagentoCountry;
import com.gfashion.magento.mapper.GfMagentoHomepageConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MagentoHomepageClient {

    @Value("${magento.url.listCategories}")
    private String listCategories;

    @Value("${magento.url.countries}")
    private String listCountries;

    @Value("${magento.url.parameters.parentIdField}")
    private String parentIdField;

    @Value("${magento.url.parameters.field}")
    private String field;

    @Value("${magento.url.parameters.value}")
    private String value;

    @Value("${magento.url.parameters.conditionType}")
    private String conditionType;

    @Value("${magento.url.parameters.chineseRootCategory}")
    private String chineseRootCategory;

    @Value("${magento.url.parameters.englishRootCategory}")
    private String englishRootCategory;

    @Value("${magento.url.parameters.supportLanguages}")
    private String supportLanguages;

    @Value("${magento.url.parameters.rootCategoryLevel}")
    private String rootCategoryLevel;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoHomepageConverter _mapper = Mappers.getMapper(GfMagentoHomepageConverter.class);

    public List<GfCategory> getCategoriesUnderParentId(String parentId) throws HomepageException {
        String field0 = String.format(field, 0, 0);
        String value0 = String.format(value, 0, 0);
        String listCategoriesById = listCategories + "?" +
                String.join("&", new ArrayList<>(Arrays.asList(new String[]{field0 + parentIdField, value0 + parentId})));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(listCategoriesById, String.class, null);
            Gson gson = new Gson();
            return this._mapper.convertMagentoCategoriesToGfCategories(gson.fromJson(responseEntity.getBody(), MagentoCategories.class).getItems());
        } catch (HttpStatusCodeException e) {
            throw new HomepageException(e.getStatusCode(), e.getMessage());
        }
    }

    public List<GfCategory> getCategories(Integer fromLevel, Integer toLevel, String locale) throws HomepageException {
        Set<String> supportLanguageSet = new HashSet<>(Arrays.asList(supportLanguages.split(",")));
        if (!supportLanguageSet.contains(locale)) {
            throw new IllegalArgumentException("The input locale is not supported. Only en or cn is supported.");
        }

        if (fromLevel > toLevel) {
            throw new IllegalArgumentException("The fromLevel should be not smaller than or equal to toLevel.");
        }

        String rootCategoryId = locale.equalsIgnoreCase("en") ? englishRootCategory : chineseRootCategory;
        Integer rootCategoryLevelInt = Integer.valueOf(rootCategoryLevel);
        String field0 = String.format(field, 0, 0);
        String value0 = String.format(value, 0, 0);
        String conditionType0 = String.format(conditionType, 0, 0);
        String field1 = String.format(field, 1, 0);
        String value1 = String.format(value, 1, 0);
        String conditionType1 = String.format(conditionType, 1, 0);
        String field2 = String.format(field, 2, 0);
        String value2 = String.format(value, 2, 0);
        String conditionType2 = String.format(conditionType, 2, 0);

        String listCategoriesByLevels = listCategories + "?" +
                String.join("&", new ArrayList<>(Arrays.asList(new String[]{
                        field0 + "path", value0 + "%" + rootCategoryId + "%", conditionType0 + "like",
                        field1 + "level", value1 + (rootCategoryLevelInt + fromLevel), conditionType1 + "gteq",
                        field2 + "level", value2 + (rootCategoryLevelInt + toLevel), conditionType2 + "lteq",
                })));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(listCategoriesByLevels, String.class, null);
            Gson gson = new Gson();
            // All Categories from Magento
            List<GfCategory> allCategories = this._mapper.convertMagentoCategoriesToGfCategories(gson.fromJson(responseEntity.getBody(), MagentoCategories.class).getItems());

            // the return parent categories;
            List<GfCategory> parentCategories = allCategories.parallelStream().filter(gfCategory -> gfCategory.getLevel() == rootCategoryLevelInt + fromLevel).collect(Collectors.toList());


            for (int delta = 0; delta < toLevel - fromLevel; delta++) {
                int currentParentLevel = rootCategoryLevelInt + fromLevel + delta;

                // get current parent categories
                List<GfCategory> currentParentCategories = allCategories.parallelStream().filter(gfCategory -> gfCategory.getLevel() == currentParentLevel).collect(Collectors.toList());

                // get all remaining non-parent categories
                allCategories = allCategories.parallelStream().filter(gfCategory -> gfCategory.getLevel() != currentParentLevel).collect(Collectors.toList());

                // build a mapping from id to parent category
                Map<Integer, GfCategory> parentCategoriesMapping = new HashMap<>();
                for (GfCategory gfParentCategory : currentParentCategories) {
                    gfParentCategory.setSubCategories(new ArrayList<>());
                    parentCategoriesMapping.put(gfParentCategory.getId(), gfParentCategory);
                }

                // insert other non-parent categories as the sub categories of parent categories
                for (GfCategory gfOtherCategory : allCategories) {
                    if (parentCategoriesMapping.containsKey(gfOtherCategory.getParent_id())) {
                        parentCategoriesMapping.get(gfOtherCategory.getParent_id()).getSubCategories().add(gfOtherCategory);
                    }
                }

                // break if no more categories to process
                if (allCategories.isEmpty()) {
                    break;
                }
            }

            return parentCategories;
        } catch (HttpStatusCodeException e) {
            throw new HomepageException(e.getStatusCode(), e.getMessage());
        }
    }

    public List<GfCountry> getCountries() throws HomepageException {
        try {
            ResponseEntity<MagentoCountry[]> responseEntity = this._restClient.exchangeGet(listCountries, MagentoCountry[].class, null);
            return this._mapper.convertMagentoCountriesToGfCountries(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            throw new HomepageException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new HomepageException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
