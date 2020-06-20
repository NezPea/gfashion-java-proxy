package com.gfashion.data.repository.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gfashion.data.GfDesignerEntity;
import com.gfashion.data.repository.dynamodb.GfDesignerRepository;
import com.gfashion.data.repository.elasticsearch.constant.Constants;
import com.gfashion.data.repository.elasticsearch.enums.Language;
import com.gfashion.data.repository.elasticsearch.mapper.ElasticsearchMapper;
import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsDesignerRepository;
import com.gfashion.data.repository.elasticsearch.repostory.EsProductRepository;
import com.gfashion.domain.elasticsearch.*;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.facet.FacetResult;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    private static final Map<Long, GfCategory> CATEGORIES = new HashMap<>();

    private static final Map<Long, GfCategory> TOP_CATEGORIES = new HashMap<>();

    private static final Map<Long, String> DESIGNERS = new HashMap<>();

    private static Long suggestTime = System.currentTimeMillis();

    @Value("${magento.url.categories}")
    private String categoryUrl;

    @Resource
    private EsProductRepository productRepository;

    @Resource
    private EsDesignerRepository designerRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private MagentoClient magentoClient;

    @Resource
    private GfDesignerRepository gfDesignerRepository;

    private ElasticsearchMapper mapper = Mappers.getMapper(ElasticsearchMapper.class);


    @PostConstruct
    public void initialize() {
        getCategoryTree();
        generateDesignerSuggestion();
    }

    public String getTopCategoryName(@NotNull Long id, @NotBlank String language) {
        Language lang = Language.valueOf(language.toLowerCase());
        String name;
        switch (lang) {
            case zh:
                // TODO return chinese name if it has
                name = TOP_CATEGORIES.get(id).getName();
                break;
            default:
                name = TOP_CATEGORIES.get(id).getName();
                break;
        }
        return name;
    }

    public Set<GfCategory> getSubCategories(@NotNull Long categoryId) {
        Set<GfCategory> subcategories = new HashSet<>();
        GfCategory gfCategory = CATEGORIES.get(categoryId);
        while (true) {
            if (gfCategory == null) {
                break;
            }
            LOGGER.info("category level={}", gfCategory.getLevel());
            if (gfCategory.getLevel() == Constants.ROOT_CATEGORY_LEVEL) {
                subcategories.add(gfCategory);
                break;
            }
            for (GfCategory category: CATEGORIES.values()) {
                if (gfCategory.getId().equals(category.getId())) {
                    LOGGER.info("category id={}, {}, parentId={}", gfCategory.getId(), category.getId(), category.getParentId());
                    subcategories.add(category);
                    gfCategory = CATEGORIES.get(category.getParentId());
                    break;
                }
            }
        }
        return subcategories;
    }

    private BoolQueryBuilder buildQueryBuilder(@NotNull GfProductSearchRequest request) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!isEmpty(request.getKeyword())) {
            String[] fields;
            if (Language.zh.toString().equals(request.getLanguage())) {
                fields = new String[]{"keyword", "nameEn", "desEn"};
            } else {
                fields = new String[]{"keyword", "nameZh", "desZh"};
            }
            queryBuilder.should(multiMatchQuery(request.getKeyword(), fields));
        }

        if (request.getTopCategoryId() != null) {
            queryBuilder.must(matchQuery("topCategoryId", request.getTopCategoryId()));
        }

        if (request.getSale() == 1) {
            queryBuilder.must(matchQuery("sale", request.getSale()));
        }

        if (request.getDesignerId() != null) {
            queryBuilder.must(matchQuery("designerId", request.getDesignerId()));
        }

        if (request.getCategoryId() != null) {
            queryBuilder.must(termQuery("categories", request.getCategoryId()));
        }

        if (!isEmpty(request.getSize())) {
            queryBuilder.must(termQuery("sizeList", request.getSize()));
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

    public GfProductSearchResponse searchWithCategories(@NotNull GfProductSearchRequest request) {
        try {
            NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder();
            nativeBuilder.withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(buildQueryBuilder(request));

            nativeBuilder.addAggregation(AggregationBuilders.terms(Constants.GROUP_CATEGORY).field("categoryId"))
                    .addAggregation(AggregationBuilders.terms(Constants.GROUP_DESIGNER).field("designerId"));

            // Page starts from 0 in elasticsearch
            nativeBuilder.withPageable(PageRequest.of(request.getPage(), request.getPageSize()));

            SearchQuery searchQuery = nativeBuilder.build();

            // Search for products
            AggregatedPage<EsProduct> products = elasticsearchTemplate.queryForPage(searchQuery, EsProduct.class);

            Set<GfDesigner> designers = new HashSet<>();
            Set<GfCategory> categories = new HashSet<>();
            GfCategory categoryTree = null;
            for (FacetResult result : products.getFacets()) {
                TermResult termResult = (TermResult) result;

                // find out designers
                if (Constants.GROUP_DESIGNER.equals(result.getName())) {
                    termResult.getTerms().forEach(t -> {
                        String id = t.getTerm();
                        GfDesigner.GfDesignerBuilder builder = GfDesigner.builder().id(Long.valueOf(id));

                        // TODO add cache for designers
                        GfDesignerEntity entity =  gfDesignerRepository.readGfDesignerEntity(id);
                        if (entity != null) {
                            // TODO show name by language
                            builder.nameEn(entity.getName()).nameZh(entity.getName());

                        }

                        designers.add(builder.build());
                    });
                }

                // find out categories
                if (Constants.GROUP_CATEGORY.equals(result.getName())) {

                    for (Term term : termResult.getTerms()) {
                        Long categoryId = Long.valueOf(term.getTerm());
                        Set<GfCategory> subcategories = getSubCategories(categoryId);
                        categories.addAll(subcategories);
                    }

                    categoryTree = toTree(categories);
                }
            }

            // assemble product page
            GfProductPage page = GfProductPage.builder()
                    .pageNo(products.getNumber() + 1)
                    .pageSize(products.getSize())
                    .total(products.getTotalElements())
                    .totalPage(products.getTotalPages())
                    .items(mapper.convertProducts(products.getContent()))
                    .build();

            // assemble search response
            return GfProductSearchResponse.builder().success(true).products(page).categories(categoryTree).designers(designers).build();
        } catch (Exception e) {
            LOGGER.error("Search product error", e);
            return GfProductSearchResponse.builder().success(false).build();
        }
    }

    public GfDesignerSuggestionResponse designerSuggestion(GfDesignerSuggestionRequest request) {
        try {
            if (System.currentTimeMillis() - suggestTime > 3600 * 12) {
                generateDesignerSuggestion();
            }

            CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion(Constants.SUGGEST_FIELD).prefix(request.getKeyword(), Fuzziness.AUTO).size(request.getQuantity());
            SearchResponse response = elasticsearchTemplate.suggest(new SuggestBuilder().addSuggestion(Constants.SUGGEST_NAME, suggestionBuilder), EsDesigner.class);
            CompletionSuggestion completionSuggestion = response.getSuggest().getSuggestion(Constants.SUGGEST_NAME);
            List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();

            List<GfDesigner> designers = new ArrayList<>();
            for (CompletionSuggestion.Entry.Option option : options) {
                try {
                    GfDesigner designer = ElasticsearchMapper.MAPPER.readValue(option.getHit().getSourceAsString(), GfDesigner.class);
                    if (designer.getTopCategoryId() != null) {
                        designer.setTopCategoryName(getTopCategoryName(designer.getTopCategoryId(), request.getLanguage()));
                    }
                    designers.add(designer);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Convert suggestion error", e);
                }
            }
            return GfDesignerSuggestionResponse.builder().success(true).data(designers).build();
        } catch (Exception e) {
            LOGGER.error("Designer suggestion error", e);
            return GfDesignerSuggestionResponse.builder().success(false).build();
        }
    }

    public GfProductRecommendationResponse recommend(GfProductRecommendationRequest request) {
        try {
            Page<EsProduct> sameCategoryProducts = productRepository.findByCategoryIdAndIdNot(request.getCategoryId(),
                    request.getProductId(), PageRequest.of(0, request.getQuantity()));

            Page<EsProduct> differentCategoryProducts = productRepository.findByCategoryIdNot(request.getCategoryId(),
                    PageRequest.of(0, request.getQuantity()));

            return GfProductRecommendationResponse.builder().success(true)
                    .sameCategoryProducts(mapper.convertProducts(sameCategoryProducts.getContent()))
                    .differentCategoryProducts(mapper.convertProducts(differentCategoryProducts.getContent()))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Get recommendation error", e);
            return GfProductRecommendationResponse.builder().success(false).build();
        }
    }

    private GfCategory toTree(Set<GfCategory> categories) {
        GfCategory root = null;

        for (GfCategory category: categories) {
            if (category.getLevel() == Constants.ROOT_CATEGORY_LEVEL) {
                root = category;
            }

            for (GfCategory cat: categories) {
                if (cat.getParentId() != null && cat.getParentId().equals(category.getId())) {
                    if (category.getChildren() == null) {
                        category.setChildren(new HashSet<>());
                    }
                    if (!containsChild(category, cat)) {
                        category.getChildren().add(cat);
                    }
                }
            }
        }
        return root;
    }

    private boolean containsChild(GfCategory category, GfCategory child) {
        if (category == null || category.getChildren() == null || category.getChildren().isEmpty() || child == null) {
            return false;
        }

        for (GfCategory cat : category.getChildren()) {
            if (cat.getId().equals(child.getId())) {
                return true;
            }
        }
        return false;
    }

    public Collection<EsDesigner> generateDesignerSuggestion() {
        Set<EsDesigner> designers = new HashSet<>();

        try {
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(matchAllQuery())
                    .addAggregation(AggregationBuilders.terms(Constants.GROUP_DESIGNER).field("designerId")
                            .subAggregation(AggregationBuilders.terms(Constants.GROUP_TOP_CATEGORY).field("topCategoryId")
                                    .subAggregation(AggregationBuilders.terms(Constants.GROUP_SALE).field("sale"))
                            )
                    )
                    .build();

            // Search for products
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);

            Terms designerTerms = aggregations.get(Constants.GROUP_DESIGNER);
            for (Terms.Bucket designerBucket : designerTerms.getBuckets()) {
                String designerId = (String) designerBucket.getKey();

                // TODO cache designers
                GfDesignerEntity entity = gfDesignerRepository.readGfDesignerEntity(designerId);
                String designerNameEn = null;
                String designerNameZh = null;
                if (entity != null) {
                    // TODO show name by language
                    designerNameEn = entity.getName();
                    designerNameZh = entity.getName();
                }

                Terms categoryTerms = designerBucket.getAggregations().get(Constants.GROUP_TOP_CATEGORY);
                for (Terms.Bucket categoryBucket : categoryTerms.getBuckets()) {
                    Long categoryId = (Long) categoryBucket.getKey();

                    Terms saleTerms = categoryBucket.getAggregations().get(Constants.GROUP_SALE);
                    for (Terms.Bucket saleBucket : saleTerms.getBuckets()) {
                        Long sale = (Long) saleBucket.getKey();
                        Long count = saleBucket.getDocCount();
                        LOGGER.debug("designerId={}, categoryId={}, sale={}, count={}", designerId, categoryId, sale, count);
                        designers.add(EsDesigner.builder().designerId(Long.valueOf(designerId))
                                .nameEn(designerNameEn).nameZh(designerNameZh)
                                .topCategoryId(categoryId).sale(sale==1).productCount(count).build());
                    }
                }
            }

            // Merge designer by designerId and topCategoryId
            Map<String, EsDesigner> summary = new HashMap<>();
            for (EsDesigner designer : designers) {
                String key = designer.getDesignerId() + "-" + designer.getTopCategoryId();

                if (summary.containsKey(key)) {
                    EsDesigner esDesigner = summary.get(key);
                    esDesigner.setProductCount(esDesigner.getProductCount() + designer.getProductCount());
                } else {
                    Set<String> suggest = new HashSet<>();
                    if (!isEmpty(designer.getNameEn())) {
                        String[] names = designer.getNameEn().split(" ");
                        for (String name: names) {
                            suggest.add(name);
                        }
                    }
                    if (!isEmpty(designer.getNameZh())) {
                        suggest.add(designer.getNameZh());
                    }
                    String[] suggests = new String[suggest.size()];

                    designer.setSuggest(new Completion(suggest.toArray(suggests)));
                    summary.put(key, designer);
                }

                if (designer.getSale()) {
                    summary.get(key).setSale(true);
                }
            }

            designerRepository.saveAll(summary.values());

            suggestTime = System.currentTimeMillis();

            return summary.values();
        } catch (Exception e) {
            LOGGER.error("Generate designer sales error.", e);
            return designers;
        }
    }

    /**
     * Get category tree from Magento
     */
    private void getCategoryTree() {
        LOGGER.info("Start to load categories from magento");
        ResponseEntity<GfCategory> response = magentoClient.exchangeGet(categoryUrl, GfCategory.class, null);
        cacheCategories(response.getBody());
        LOGGER.info("Load categories over");
    }

    private void cacheCategories(GfCategory category) {
        if (category == null) {
            return;
        }

        CATEGORIES.put(category.getId(), category);

        if (category.getLevel() == Constants.TOP_CATEGORY_LEVEL) {
            TOP_CATEGORIES.put(category.getId(), category);
        }

        if (category.getChildren() == null || category.getChildren().isEmpty()) {
            return;
        }

        for (GfCategory cat : category.getChildren()) {
            cacheCategories(cat);
        }

        category.setChildren(null);
    }

    private boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }



    /** ------------- methods down below for mocking data ------------------- */

    public void mockProduct() {
        EsProduct product = EsProduct.builder()
                .id("100")
                .price(10000)
                .desEn("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                .nameEn("Black Wool Herris Trousers")
                .smallPic("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M205048_1.jpg")
                .categories(new Long[]{789L, 1070L, 1074L, 1081L})
                .categoryId(1081L)
                .topCategoryId(789L)
                .sale(1)
                .designerId(101L)
                .sizeList(new String[]{"S", "M", "L", "XL", "XXL"})
                .build();

        EsProduct product1 = EsProduct.builder()
                .id("101")
                .price(15000)
                .desEn("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.")
                .nameEn("Black Dimensional Out Pocket Cargo Pants")
                .smallPic("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M213021_1.jpg")
                .categories(new Long[]{789L, 1070L, 1074L, 1082L})
                .categoryId(1082L)
                .topCategoryId(789L)
                .sale(0)
                .designerId(102L)
                .sizeList(new String[]{"36", "37", "38"})
                .build();

        List<EsProduct> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        productRepository.saveAll(products);
    }

    public void mockDesigner() {
        EsDesigner designer = EsDesigner.builder().id(100L).designerId(100L).nameZh("李世民").suggest(new Completion(new String[]{"李世明"})).briefEn("Alumna of prestigious Parisian houses Maison Margiela, Dior, and Balenciaga, Marine Serre debuted her first collection, ‘15-21’, during her fourth year at La Cambre Mode in Brussels. Since, the French designer has established her namesake label as one to watch, garnering the LVMH Prize for Young Fashion Designers in 2017 and launching a menswear line for Spring/Summer 2019. Before pursuing a career in fashion design, Serre initially intended on becoming a professional tennis player. This proclivity for athletics features prominently across Serre’s collections: second-skin tops, leggings, and jet caps, all emblazoned with her instantly-recognizable moon logo, have become Serre’s calling card. Her men’s ready-to-wear offering sees deconstructed chore jackets and parkas referencing the convergence of sportswear and workwear, with all-over logo jeans, bike shorts, and plush jackets summoning 90’s nostalgia. Collaborative sneakers with sportswear giants Nike and Converse round out Serre’s collection of genderless separates.").build();
        EsDesigner designer1 = EsDesigner.builder().id(101L).designerId(101L).nameEn("Nirvana").suggest(new Completion(new String[]{"Nirvana"})).briefEn("Phillip Lim launched his namesake collection in 2005, naming it “3.1” after his 31 years of age. His first menswear collection debuted shortly after, in 2007. The New York-based designer counts his city as his chief inspiration, but the optimistic, laid-back attitude of Lim’s native California informs his sophisticated yet approachable designs. Fusions of sportswear and tailoring join classic pieces reworked with a minimalist finish. Oversized cuts, graphic colorblocking, and directional prints infuse Lim’s motorcycle and bomber jackets, slim lounge pants, and signature 31 Hour bags with a modern, masculine sleekness.").build();
        EsDesigner designer2 = EsDesigner.builder().id(102L).designerId(102L).nameEn("Nevermind Never").suggest(new Completion(new String[]{"Nevermind Never"})).briefEn("Based out of New York City, Abasi Rosborough is the namesake menswear brand of Abdul Abasi and Greg Rosborough, who met during their studies at the Fashion Institute of Technology. Following several years spent respectively honing their skills with Ralph Lauren and Engineered Garments, Rosborough and Abasi initiated their collaboration and released their first concept-driven collection in 2013. The pair has since further refined their sartorial output with pieces in simple shapes and progressive cuts that echo a multitude of influences, including sportswear, military uniforms, and classic suiting. Their offering of unadorned but precisely panelled shirts, jackets, and sarouel-style trousers in black and neutral tones fulfills the need for a modular wardrobe of separates that layer seamlessly while making a statement on their own – a pragmatic ideal for the urban sophisticate.").build();
        List<EsDesigner> designers = new ArrayList<>();
        designers.add(designer);
        designers.add(designer1);
        designers.add(designer2);
        designerRepository.saveAll(designers);
    }

    public void cleanupProducts() {
//        productRepository.deleteById("100");
//        productRepository.deleteById("101");
        productRepository.deleteAll();
    }

    public void cleanupDesigners() {
        designerRepository.deleteAll();
    }
}
