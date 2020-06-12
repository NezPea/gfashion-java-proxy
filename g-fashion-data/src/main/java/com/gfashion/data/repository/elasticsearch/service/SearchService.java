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
import javax.validation.constraints.NotNull;

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
        EsProduct product = EsProduct.builder()
                .id("100")
                .brandId("100")
                .price(10000)
                .brandName("channel")
                .brief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                .name("Black Wool Herris Trousers")
                .categories(new String[]{"clothing", "trousers"})
                .gender("F")
                .sale(1)
                .designerId("1003239")
                .size("XXL")
                .language("en")
                .build();

        EsProduct product1 = EsProduct.builder()
                .id("101")
                .brandId("101")
                .price(15000)
                .brandName("adidass")
                .brief("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.")
                .name("Black Dimensional Out Pocket Cargo Pants")
                .categories(new String[]{"bags", "shoes"})
                .gender("F")
                .sale(0)
                .designerId("09a88ser2")
                .size("36")
                .language("cn")
                .build();

        List<EsProduct> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        productRepository.saveAll(products);
    }

    public void cleanup() {
        productRepository.deleteById("100");
        productRepository.deleteById("101");
    }

    public Page<EsProduct> search(@NotNull GfProductSearchRequest request) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!isEmpty(request.getKeyword())) {
            queryBuilder.should(multiMatchQuery(request.getKeyword(), "name", "brief"));
        }

        if (!isEmpty(request.getGender())) {
            queryBuilder.must(matchQuery("gender", request.getGender()));
        }

        if (request.getSale() == 1) {
            queryBuilder.must(matchQuery("sale", request.getSale()));
        }

        if (!isEmpty(request.getDesignerId())) {
            queryBuilder.must(matchQuery("designerId", request.getDesignerId()));
        }

        if (!isEmpty(request.getCategoryId())) {
            queryBuilder.must(termQuery("categories", request.getCategoryId()));
        }

        if (!isEmpty(request.getLanguage())) {
            queryBuilder.must(matchQuery("language", request.getLanguage()));
        }

        if (!isEmpty(request.getSize())) {
            queryBuilder.must(matchQuery("size", request.getSize()));
        }

        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder();
        nativeBuilder.withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(queryBuilder);

        if (!isEmpty(request.getSorting())) {
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

    private boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }
}
