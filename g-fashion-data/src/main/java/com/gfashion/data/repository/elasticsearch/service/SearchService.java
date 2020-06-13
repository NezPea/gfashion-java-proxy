package com.gfashion.data.repository.elasticsearch.service;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import com.gfashion.data.repository.elasticsearch.mapper.ElasticsearchMapper;
import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsDesignerRepository;
import com.gfashion.data.repository.elasticsearch.repostory.EsProductRepository;
import com.gfashion.domain.elasticsearch.GfCategory;
import com.gfashion.domain.elasticsearch.GfDesigner;
import com.gfashion.domain.elasticsearch.GfProductPage;
import com.gfashion.domain.elasticsearch.GfProductSearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.facet.FacetResult;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SearchService {

    @Resource
    private EsProductRepository productRepository;

    @Resource
    private EsDesignerRepository designerRepository;

    @Resource
    private ElasticsearchOperations elasticsearchTemplate;

    private ElasticsearchMapper mapper = Mappers.getMapper(ElasticsearchMapper.class);

    public Set<EsCategory> getCategories(@NotBlank String language) {
        Set<EsCategory> categories = new HashSet<>();
        categories.add(EsCategory.builder().id(100).name("Clothing").weight(1).build());
        categories.add(EsCategory.builder().id(1001).name("Trousers").parentId(100).weight(10).build());
        categories.add(EsCategory.builder().id(101).name("Accessory").weight(2).build());
        categories.add(EsCategory.builder().id(1011).name("Bags").parentId(101).weight(20).build());
        return categories;
    }

    public Set<GfCategory> getSubCategoryTree(@NotNull Integer categoryId, @NotNull Set<EsCategory> tree) {
        Set<GfCategory> subTree = new HashSet<>();
        while (categoryId != null) {
            for (EsCategory category: tree) {
                if (categoryId.equals(category.getId())) {
                    subTree.add(mapper.convertCategory(category));
                    categoryId = category.getParentId();
                    break;
                }
            }
        }
        return subTree;
    }

    public void mockData() {
        EsProduct product = EsProduct.builder()
                .id("100")
                .brandId("100")
                .price(10000)
                .brandName("channel")
                .brief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                .name("Black Wool Herris Trousers")
                .categories(new Integer[]{100, 1001})
                .category(1001)
                .gender("F")
                .sale(1)
                .designerId(1003239)
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
                .categories(new Integer[]{101, 1011})
                .category(1011)
                .gender("F")
                .sale(0)
                .designerId(900)
                .size("36")
                .language("cn")
                .build();

        List<EsProduct> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        productRepository.saveAll(products);
    }

    public void cleanup() {
//        productRepository.deleteById("100");
//        productRepository.deleteById("101");

        productRepository.deleteAll();
    }

    private BoolQueryBuilder buildQueryBuilder(@NotNull GfProductSearchRequest request) {
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
        return queryBuilder;
    }

    public GfProductPage search(@NotNull GfProductSearchRequest request) {
        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder();
        nativeBuilder.withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(buildQueryBuilder(request));

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

        Page<EsProduct> products = elasticsearchTemplate.queryForPage(searchQuery, EsProduct.class);

        return GfProductPage.builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .total(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .items(mapper.convertProducts(products.getContent()))
                .build();
    }

    public GfProductPage searchWithCategories(@NotNull GfProductSearchRequest request) {
        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder();
        nativeBuilder.withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(buildQueryBuilder(request));

        nativeBuilder.addAggregation(AggregationBuilders.terms(Constants.GROUP_CATEGORY).field("category"))
                .addAggregation(AggregationBuilders.terms(Constants.GROUP_DESIGNER).field("designerId"));

        // Page starts from 0 in elasticsearch
        nativeBuilder.withPageable(PageRequest.of(request.getPage(), request.getPageSize()));

        SearchQuery searchQuery = nativeBuilder.build();

        AggregatedPage<EsProduct> products = (AggregatedPage) elasticsearchTemplate.queryForPage(searchQuery, EsProduct.class);

        Set<GfDesigner> designers = new HashSet<>();
        Set<GfCategory> categories = new HashSet<>();

        for (FacetResult result : products.getFacets()) {
            TermResult termResult = (TermResult) result;

            // find out designers
            if (Constants.GROUP_DESIGNER.equals(result.getName())) {
                termResult.getTerms().forEach(t -> designers.add(GfDesigner.builder().id(Integer.valueOf(t.getTerm())).build()));
            }

            // find out categories
            if (Constants.GROUP_CATEGORY.equals(result.getName())) {
                Set<EsCategory> categoryTree = getCategories(request.getLanguage());

                for (Term term : termResult.getTerms()) {
                    Integer categoryId = Integer.valueOf(term.getTerm());
                    Set<GfCategory> subCategoryTree = getSubCategoryTree(categoryId, categoryTree);
                    categories.addAll(subCategoryTree);
                }
            }
        }

        return GfProductPage.builder()
                .pageNo(products.getNumber() + 1)
                .pageSize(products.getSize())
                .total(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .items(mapper.convertProducts(products.getContent()))
                .categories(categories)
                .designers(designers)
                .build();
    }

    private boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }
}
