package com.gfashion.data.repository.elasticsearch.service;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsDesignerRepository;
import com.gfashion.data.repository.elasticsearch.repostory.EsProductRepository;
import com.gfashion.domain.elasticsearch.GfProduct;
import com.gfashion.domain.elasticsearch.GfProductPage;
import com.gfashion.domain.elasticsearch.GfProductSearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SearchService {

    @Resource
    private EsProductRepository productRepository;

    @Resource
    private EsDesignerRepository designerRepository;

    @Resource
    private ElasticsearchOperations elasticsearchTemplate;


    public void mockData() {
        EsProduct product = new EsProduct();
        product.setId("100");
        product.setBrandId("100");
        product.setPrice(10000);
        product.setBrandName("channel");
        product.setBrief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.");
        product.setName("Black Wool Herris Trousers");
        product.setCategories(new String[]{"clothing", "trousers"});
        product.setGender("F");
        product.setSale(1);
        product.setDesignerId("1003239");
        product.setSize("XXL");
        product.setLanguage("en");

        EsProduct product1 = new EsProduct();
        product1.setId("101");
        product1.setBrandId("101");
        product1.setPrice(15000);
        product1.setBrandName("adidass");
        product1.setBrief("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.");
        product1.setName("Black Dimensional Out Pocket Cargo Pants");
        product1.setCategories(new String[]{"bags", "shoes"});
        product1.setGender("F");
        product1.setSale(0);
        product1.setDesignerId("09a88ser2");
        product1.setSize("36");
        product1.setLanguage("cn");

        List<EsProduct> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        productRepository.saveAll(products);
    }

    public void cleanup() {
        productRepository.deleteAll();
    }

    public Page<EsProduct> search(GfProductSearchRequest request) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (request.getKeyword() != null && request.getKeyword().trim().length() != 0) {
            queryBuilder.should(multiMatchQuery(request.getKeyword(), "name", "brief"));
        }

        if (request.getGender() != null && request.getGender().trim().length() != 0) {
            queryBuilder.must(matchQuery("gender", request.getGender()));
        }

        if (request.getSale() != null) {
            queryBuilder.must(matchQuery("sale", request.getSale()));
        }

        if (request.getDesignerId() != null && request.getDesignerId().trim().length() != 0) {
            queryBuilder.must(matchQuery("designerId", request.getDesignerId()));
        }

        if (request.getCategoryId() != null && request.getCategoryId().trim().length() != 0) {
            queryBuilder.must(termQuery("categories", request.getCategoryId()));
        }

        if (request.getLanguage() != null && request.getLanguage().trim().length() != 0) {
            queryBuilder.must(matchQuery("language", request.getLanguage()));
        }

        if (request.getSize() != null && request.getSize().trim().length() != 0) {
            queryBuilder.must(matchQuery("size", request.getSize()));
        }

        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder();
        nativeBuilder.withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(queryBuilder);

        if (request.getSorting() != null && request.getSorting().trim().length() != 0) {
            SortOrder sortOrder = SortOrder.ASC;
            if (SortOrder.ASC.toString().equals(request.getOrder()) || SortOrder.DESC.toString().equals(request.getOrder())) {
                sortOrder = SortOrder.fromString(request.getOrder());
            }
            nativeBuilder.withSort(SortBuilders.fieldSort(request.getSorting()).order(sortOrder));
        }

        // Page starts from 0 in elasticsearch
        nativeBuilder.withPageable(PageRequest.of(request.getPage(), request.getPageSize()));

        SearchQuery searchQuery = nativeBuilder.build();

        return elasticsearchTemplate.queryForPage(searchQuery, EsProduct.class);
    }

    public GfProductPage transform(Page<EsProduct> products) {
        List<GfProduct> items = products.getContent().stream().map(p -> {
            GfProduct gfProduct = new GfProduct();
            BeanUtils.copyProperties(p, gfProduct);
            return gfProduct;
        }).collect(Collectors.toList());

        return GfProductPage.builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .total(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .items(items)
                .build();
    }
}
