package com.gfashion.data.repository.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gfashion.data.repository.elasticsearch.constant.Constants;
import com.gfashion.data.repository.elasticsearch.enums.Language;
import com.gfashion.data.repository.elasticsearch.mapper.ElasticsearchMapper;
import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsDesignerRepository;
import com.gfashion.data.repository.elasticsearch.repostory.EsProductRepository;
import com.gfashion.domain.elasticsearch.*;
import org.apache.tomcat.util.bcel.Const;
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
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.facet.FacetResult;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

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

    private ElasticsearchMapper mapper = Mappers.getMapper(ElasticsearchMapper.class);

    public Set<EsCategory> getCategories(@NotBlank String language) {
        Language lang = Language.valueOf(language.toLowerCase());
        Set<EsCategory> categories = new HashSet<>();
        switch (lang) {
            case en:
                categories.add(EsCategory.builder().id(10).name("Clothing").weight(1).brief("Pragmatism and versatility connect the Fall collections on offer from our menswear designers, with modern meshings of shrewd tailoring, performance-driven athleticwear, and experimentally proportioned sportswear making up an adaptable whole. Outerwear bridges the traditional and the technical. Hooded anoraks, raincoats, and down-filled puffer jackets rendered in technical materials exhibit hyper-utilitarian engineering, while perennial wool peacoats, leather motorcycle jackets, and shearling jackets answer all style scenarios. For the tailoring-inclined, shirts and suiting in purist forms get elevated to statement status with bondage details, straps, and buckle accents, while graphic embellishments and embroidery adorn quilted bombers, wool and cashmere cardigans, and crewneck pullovers. Taking streetwear into new contexts are tracksuits, zip-ups, and hoodies that offer subculture symbolism and technical invention. Denim has a grunge appeal: Sulfur washes and ripped distressing are confidently casual, and tapered, wide-leg, and cropped fits exude an eclectic new masculinity.").build());
                categories.add(EsCategory.builder().id(100).name("Top").parentId(10).weight(1).brief("Belts and suspenders alike arrive in a wealth of colors, shapes, and styles to function as the quintessential accessory to complete any menswear ensemble. Slender buffed and embossed calfskin options in neutral tones fit seamlessly with smart casualwear, while wide leather renditions bring bold styling opportunities with eye-catching hardware. Carved and engraved statement buckles pair wonderfully with signature studded accents ranging from brilliant golden tones to antiqued shades of silver. Timeless and ever-necessary, designer men's belts and suspenders gift the wearer a unique occasion to extend the breadth of their sophistication to new paths. Modernity is achieved through minimal two-tone panels and extended lengths, effortlessly advancing one's own styling agenda. Copious classic renditions remain available for those who abhor trends and wish to perpetuate the traditional polish of years past.").build());
                categories.add(EsCategory.builder().id(102).name("Trousers").parentId(10).weight(3).brief("To thoughtfully curate the spaces that one inhabits is a wholly worthwhile practice that not only enhances the experience within the space, but provides new channels for meaning-making on a personal level. This can be actualized by widening the scope of self-realization to include those elements normally abandoned to the periphery: housewares, such as designer blankets, present one with an opportunity to promulgate personal stylistic tastes and pay homage to those elements that often go uncelebrated. The immense physical comfort and warmth achieved by handcrafted cashmere options, although impressive, is second only to the confidence realized through the assemblage of cultivated tastes. Iconic jacquard digital prints cater to design enthusiasts who seek to inject attitude into their living space while simple striped woolen throws harken concepts integral to timeless heritage.").build());
                categories.add(EsCategory.builder().id(1001).name("Polo").parentId(100).weight(10).brief("The practice of adorning one’s canine counterpart in decorative and functional garb dates back to the Egyptian predynastic period. Revered in many civilizations as man’s most loyal companion, the dog has been bedecked with accessories throughout history to signify ownership and competence in combat, or simply as a means of reflecting its owner’s stylistic inclinations. In the contemporary age, dog accessories have expanded from traditional collars, leashes, and tags, to include designer hoodies, t-shirts, and travel bags, catering to the needs of modern dog ownership. With the well-being of our four-legged friends at the core of their design processes, luxury labels offer garments that protect from the elements and insulate against harsh temperatures. In addition to these integral functional purposes, the latest selection of luxury accessories enable dogs to partake in streetwear and high fashion’s most current happenings alongside their owners. Diverging from conventional approaches to dog accessories, designers offer logo-embellished leashes and experiment with vinyl-constructed dog jackets in vibrant colorways. With the rise of ‘pet influencers’ and twinning with one’s pooch, the luxury alternative of dog accessories guarantees stand-out impact.").build());
                categories.add(EsCategory.builder().id(1002).name("Sweeter").parentId(100).weight(11).brief("Men's contemporary eyewear provides a seemingly limitless array of potentialities to augment one's personal style in a way that honors both form and function. Classically urbane or distinctly versatile, our selection of men's sunglasses and optical glasses exhibit exquisite craftsmanship while exploring new frontiers of self expression. Pairs exist for those in search of eyewear that would best compliment their own facial structure as well as for those who seek to challenge notions of traditionalism, favoring the opportunity to make a statement through an otherwise utilitarian accessory. Iconic wire-frame aviators and classic square frames are given modernistic updates, while delicate gold frames and smoky lenses manifest masculine magnetism. All degrees of sartorial styling are waiting to be realized through a pair of men's eyewear.").build());
                categories.add(EsCategory.builder().id(1003).name("Jack").parentId(100).weight(12).brief("Whether simply providing warmth in the colder seasons or lending stylish flair behind the steering wheel, designer men’s gloves provide significant styling opportunities for the modern man. Proposing varied options for protection against the elements, meticulously-crafted wool pairs equip the wearer with rugged sophistication while fingerless counterparts allow for optimal dexterity on-the-go. High-performance and hyper-utilitarianism is achieved through contemporary men’s gloves that defy simply definition. Designers explore new technical fabrications to enhance what were previously thought of as the definite conventions of hand-wear. Alternatively, rough-edged appeal is preserved through a strict adherence to traditional desires and manufacturing practices that produce a commodity which wholly encapsulates classic Americana. Ranging anywhere from sporty to sophisticated, men’s designer gloves are available for any proclivity.").build());
                categories.add(EsCategory.builder().id(11).name("Beauty").weight(1).brief("Designer men's hats, from the classic to the contemporary, are the true finishing touch to any considered outfit. Although no longer intrinsically linked to concepts of social status, hats still carry the ability to redefine any outfit and elevate one's personal style. Available in an immeasurable number of styles, fabrics and colors, contemporary men's hats reinterpret historically significant headwear for a new audience. Hybrid pieces harmoniously reject categorization and let streetwear clout bleed into sophisticated design. Sumptuous fabrications achieve new levels of individual luxury and ensure utmost comfort in Alpine environments. Comparatively, fitted baseball caps carry colorful appliqués to succinctly convey a message wherever they go. Now removed from their former social sensibilities, designer men's hats ensure that the grandeur never fades.").build());
                categories.add(EsCategory.builder().id(101).name("Accessory").parentId(11).weight(2).brief("Treading a line between high-end appeal and irreverent attitude, our selection of menswear accessories runs the gamut from quiet and minimalist refinement to audacious creativity. Wide-brimmed fedoras, harness belts, and studded wraparound bracelets complement fringed scarves, cozy knit beanies, and fingerless gloves, while travel-ready timepieces, modernist eyewear, and iPhone cases showcase the futuristic touches of everyday lifestyle. Pocket essentials including bifold wallets, embossed card holders, money clips, and keychains enhance clean and simple design with subtly tongue-in-cheek details, in contrast to the rugged delicacy of understated necklaces and masculine carved rings. Together, they define a new and contemporary vision of elegance.").build());
                categories.add(EsCategory.builder().id(1011).name("Bags").parentId(101).weight(20).brief("Skewing from simple and understated to raw and rugged, designer men's jewelry is a subtle yet indispensible addition to any considered aesthetic. Burnished silver-tones and engraved pieces underscore nonchalant styling, while an enviable vintage appeal is achieved through signature carved monogram detailing. To expertly complete any outfit, contemporary men's jewelry is an essential element. Consummate timelessness is accomplished through classic pendants and masterful detailing. Strike a balance through precisely paired accent pieces or forego conservatism in favor of opulence by adorning a rich cornucopia of jeweled statement makers. Whether seeking an advanced sense of minimalism or envisioning a rebellious neo-punk edge, embroidered and embossed leatherworks as well as crystal-cut Swarovski accents advance an eclectic spirit.").build());
                break;
            case zh:
                categories.add(EsCategory.builder().id(10).name("衣服").weight(1).brief("Pragmatism and versatility connect the Fall collections on offer from our menswear designers, with modern meshings of shrewd tailoring, performance-driven athleticwear, and experimentally proportioned sportswear making up an adaptable whole. Outerwear bridges the traditional and the technical. Hooded anoraks, raincoats, and down-filled puffer jackets rendered in technical materials exhibit hyper-utilitarian engineering, while perennial wool peacoats, leather motorcycle jackets, and shearling jackets answer all style scenarios. For the tailoring-inclined, shirts and suiting in purist forms get elevated to statement status with bondage details, straps, and buckle accents, while graphic embellishments and embroidery adorn quilted bombers, wool and cashmere cardigans, and crewneck pullovers. Taking streetwear into new contexts are tracksuits, zip-ups, and hoodies that offer subculture symbolism and technical invention. Denim has a grunge appeal: Sulfur washes and ripped distressing are confidently casual, and tapered, wide-leg, and cropped fits exude an eclectic new masculinity.").build());
                categories.add(EsCategory.builder().id(100).name("上衣").parentId(10).weight(1).brief("Belts and suspenders alike arrive in a wealth of colors, shapes, and styles to function as the quintessential accessory to complete any menswear ensemble. Slender buffed and embossed calfskin options in neutral tones fit seamlessly with smart casualwear, while wide leather renditions bring bold styling opportunities with eye-catching hardware. Carved and engraved statement buckles pair wonderfully with signature studded accents ranging from brilliant golden tones to antiqued shades of silver. Timeless and ever-necessary, designer men's belts and suspenders gift the wearer a unique occasion to extend the breadth of their sophistication to new paths. Modernity is achieved through minimal two-tone panels and extended lengths, effortlessly advancing one's own styling agenda. Copious classic renditions remain available for those who abhor trends and wish to perpetuate the traditional polish of years past.").build());
                categories.add(EsCategory.builder().id(102).name("裤子").parentId(10).weight(3).brief("To thoughtfully curate the spaces that one inhabits is a wholly worthwhile practice that not only enhances the experience within the space, but provides new channels for meaning-making on a personal level. This can be actualized by widening the scope of self-realization to include those elements normally abandoned to the periphery: housewares, such as designer blankets, present one with an opportunity to promulgate personal stylistic tastes and pay homage to those elements that often go uncelebrated. The immense physical comfort and warmth achieved by handcrafted cashmere options, although impressive, is second only to the confidence realized through the assemblage of cultivated tastes. Iconic jacquard digital prints cater to design enthusiasts who seek to inject attitude into their living space while simple striped woolen throws harken concepts integral to timeless heritage.").build());
                categories.add(EsCategory.builder().id(1001).name("圆领衫").parentId(100).weight(10).brief("The practice of adorning one’s canine counterpart in decorative and functional garb dates back to the Egyptian predynastic period. Revered in many civilizations as man’s most loyal companion, the dog has been bedecked with accessories throughout history to signify ownership and competence in combat, or simply as a means of reflecting its owner’s stylistic inclinations. In the contemporary age, dog accessories have expanded from traditional collars, leashes, and tags, to include designer hoodies, t-shirts, and travel bags, catering to the needs of modern dog ownership. With the well-being of our four-legged friends at the core of their design processes, luxury labels offer garments that protect from the elements and insulate against harsh temperatures. In addition to these integral functional purposes, the latest selection of luxury accessories enable dogs to partake in streetwear and high fashion’s most current happenings alongside their owners. Diverging from conventional approaches to dog accessories, designers offer logo-embellished leashes and experiment with vinyl-constructed dog jackets in vibrant colorways. With the rise of ‘pet influencers’ and twinning with one’s pooch, the luxury alternative of dog accessories guarantees stand-out impact.").build());
                categories.add(EsCategory.builder().id(1002).name("毛衣").parentId(100).weight(11).brief("Men's contemporary eyewear provides a seemingly limitless array of potentialities to augment one's personal style in a way that honors both form and function. Classically urbane or distinctly versatile, our selection of men's sunglasses and optical glasses exhibit exquisite craftsmanship while exploring new frontiers of self expression. Pairs exist for those in search of eyewear that would best compliment their own facial structure as well as for those who seek to challenge notions of traditionalism, favoring the opportunity to make a statement through an otherwise utilitarian accessory. Iconic wire-frame aviators and classic square frames are given modernistic updates, while delicate gold frames and smoky lenses manifest masculine magnetism. All degrees of sartorial styling are waiting to be realized through a pair of men's eyewear.").build());
                categories.add(EsCategory.builder().id(1003).name("夹克").parentId(100).weight(12).brief("Whether simply providing warmth in the colder seasons or lending stylish flair behind the steering wheel, designer men’s gloves provide significant styling opportunities for the modern man. Proposing varied options for protection against the elements, meticulously-crafted wool pairs equip the wearer with rugged sophistication while fingerless counterparts allow for optimal dexterity on-the-go. High-performance and hyper-utilitarianism is achieved through contemporary men’s gloves that defy simply definition. Designers explore new technical fabrications to enhance what were previously thought of as the definite conventions of hand-wear. Alternatively, rough-edged appeal is preserved through a strict adherence to traditional desires and manufacturing practices that produce a commodity which wholly encapsulates classic Americana. Ranging anywhere from sporty to sophisticated, men’s designer gloves are available for any proclivity.").build());
                categories.add(EsCategory.builder().id(11).name("女性").weight(1).brief("Designer men's hats, from the classic to the contemporary, are the true finishing touch to any considered outfit. Although no longer intrinsically linked to concepts of social status, hats still carry the ability to redefine any outfit and elevate one's personal style. Available in an immeasurable number of styles, fabrics and colors, contemporary men's hats reinterpret historically significant headwear for a new audience. Hybrid pieces harmoniously reject categorization and let streetwear clout bleed into sophisticated design. Sumptuous fabrications achieve new levels of individual luxury and ensure utmost comfort in Alpine environments. Comparatively, fitted baseball caps carry colorful appliqués to succinctly convey a message wherever they go. Now removed from their former social sensibilities, designer men's hats ensure that the grandeur never fades.").build());
                categories.add(EsCategory.builder().id(101).name("首饰").parentId(11).weight(2).brief("Treading a line between high-end appeal and irreverent attitude, our selection of menswear accessories runs the gamut from quiet and minimalist refinement to audacious creativity. Wide-brimmed fedoras, harness belts, and studded wraparound bracelets complement fringed scarves, cozy knit beanies, and fingerless gloves, while travel-ready timepieces, modernist eyewear, and iPhone cases showcase the futuristic touches of everyday lifestyle. Pocket essentials including bifold wallets, embossed card holders, money clips, and keychains enhance clean and simple design with subtly tongue-in-cheek details, in contrast to the rugged delicacy of understated necklaces and masculine carved rings. Together, they define a new and contemporary vision of elegance.").build());
                categories.add(EsCategory.builder().id(1011).name("包包").parentId(101).weight(20).brief("Skewing from simple and understated to raw and rugged, designer men's jewelry is a subtle yet indispensible addition to any considered aesthetic. Burnished silver-tones and engraved pieces underscore nonchalant styling, while an enviable vintage appeal is achieved through signature carved monogram detailing. To expertly complete any outfit, contemporary men's jewelry is an essential element. Consummate timelessness is accomplished through classic pendants and masterful detailing. Strike a balance through precisely paired accent pieces or forego conservatism in favor of opulence by adorning a rich cornucopia of jeweled statement makers. Whether seeking an advanced sense of minimalism or envisioning a rebellious neo-punk edge, embroidered and embossed leatherworks as well as crystal-cut Swarovski accents advance an eclectic spirit.").build());
                break;
            default:
                break;
        }
        return categories;
    }

    public Map<Integer, String> getMajorCategories(@NotBlank String language) {
        Language lang = Language.valueOf(language.toLowerCase());
        Map<Integer, String> catalog = new HashMap<>();
        switch (lang) {
            case en:
                catalog.put(1, "MEN");
                catalog.put(2, "WOMEN");
                catalog.put(3, "CRAFTS");
                catalog.put(4, "INDUSTRY");
                break;
            case zh:
                catalog.put(1, "男士");
                catalog.put(2, "女士");
                catalog.put(3, "工艺");
                catalog.put(4, "工业");
                break;
            default:
                break;
        }
        return catalog;
    }

    public String getMajorCategory(@NotNull Integer id, @NotBlank String language) {
        return getMajorCategories(language).get(id);
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

        if (!isEmpty(request.getSize())) {
            queryBuilder.must(termQuery("size", request.getSize()));
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

            nativeBuilder.addAggregation(AggregationBuilders.terms(Constants.GROUP_CATEGORY).field("category"))
                    .addAggregation(AggregationBuilders.terms(Constants.GROUP_DESIGNER).field("designerId"));

            // Page starts from 0 in elasticsearch
            nativeBuilder.withPageable(PageRequest.of(request.getPage(), request.getPageSize()));

            SearchQuery searchQuery = nativeBuilder.build();

            // Search for products
            AggregatedPage<EsProduct> products = elasticsearchTemplate.queryForPage(searchQuery, EsProduct.class);

            Set<GfDesigner> designers = new HashSet<>();
            Set<GfCategory> categories = new HashSet<>();

            for (FacetResult result : products.getFacets()) {
                TermResult termResult = (TermResult) result;

                // find out designers
                if (Constants.GROUP_DESIGNER.equals(result.getName())) {
                    termResult.getTerms().forEach(t -> {
                        String id = t.getTerm();
                        designerRepository.findById(id).ifPresent((designer) ->
                                designers.add(mapper.convertDesigner(designer))
                        );
                    });
                }

                // find out categories
                if (Constants.GROUP_CATEGORY.equals(result.getName())) {
                    Set<EsCategory> categoryTree = getCategories(request.getLanguage());

                    for (Term term : termResult.getTerms()) {
                        Integer categoryId = Integer.valueOf(term.getTerm());
                        Set<GfCategory> subCategoryTree = getSubCategoryTree(categoryId, categoryTree);
                        categories.addAll(subCategoryTree);
                    }

                    categories = toTree(categories);
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
            return GfProductSearchResponse.builder().success(true).products(page).categories(categories).designers(designers).build();
        } catch (Exception e) {
            LOGGER.error("Search product error", e);
            return GfProductSearchResponse.builder().success(false).build();
        }
    }

    public GfDesignerSuggestionResponse designerSuggestion(GfDesignerSuggestionRequest request) {
        try {
            CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion(Constants.SUGGEST_FIELD).prefix(request.getKeyword(), Fuzziness.AUTO).size(10);
            SearchResponse response = elasticsearchTemplate.suggest(new SuggestBuilder().addSuggestion(Constants.SUGGEST_NAME, suggestionBuilder), EsDesigner.class);
            CompletionSuggestion completionSuggestion = response.getSuggest().getSuggestion(Constants.SUGGEST_NAME);
            List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();

            List<GfDesigner> designers = new ArrayList<>();
            for (CompletionSuggestion.Entry.Option option : options) {
                try {
                    GfDesigner designer = ElasticsearchMapper.MAPPER.readValue(option.getHit().getSourceAsString(), GfDesigner.class);
                    if (designer.getMajorCategoryId() != null) {
                        designer.setMajorCategoryName(getMajorCategory(designer.getMajorCategoryId(), request.getLanguage()));
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

    private Set<GfCategory> toTree(Set<GfCategory> categories) {
        Set<GfCategory> tree = new HashSet<>();

        for (GfCategory category: categories) {
            if (category.getParentId() == null) {
                tree.add(category);
            }

            for (GfCategory cat: categories) {
                if (cat.getParentId() != null && cat.getParentId().equals(category.getId())) {
                    if (category.getChildren() == null) {
                        category.setChildren(new HashSet<>());
                    }
                    category.getChildren().add(cat);
                }
            }
        }
        return tree;
    }

    public Collection<EsDesigner> generateDesigners() {
        Set<EsDesigner> designers = new HashSet<>();

        try {
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices(Constants.INDEX_PRODUCT).withTypes(Constants.TYPE).withQuery(matchAllQuery())
                    .addAggregation(AggregationBuilders.terms(Constants.GROUP_DESIGNER).field("designerId")
                            .subAggregation(AggregationBuilders.terms(Constants.GROUP_TOP_CATEGORY).field("majorCategory")
                                    .subAggregation(AggregationBuilders.terms(Constants.GROUP_SALE).field("sale"))
                            )
                    )
                    .build();

            // Search for products
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);

            Terms designerTerms = aggregations.get(Constants.GROUP_DESIGNER);
            designerTerms.getBuckets().forEach(designerBucket -> {
                String designerId = (String) designerBucket.getKey();

                Terms categoryTerms = designerBucket.getAggregations().get(Constants.GROUP_TOP_CATEGORY);
                categoryTerms.getBuckets().forEach(categoryBucket -> {
                    Long categoryId = (Long) categoryBucket.getKey();

                    Terms saleTerms = categoryBucket.getAggregations().get(Constants.GROUP_SALE);
                    saleTerms.getBuckets().forEach(saleBucket -> {
                        Long sale = (Long) saleBucket.getKey();
                        Long count = saleBucket.getDocCount();
                        LOGGER.debug("designerId={}, categoryId={}, sale={}, count={}", designerId, categoryId, sale, count);
                        designers.add(EsDesigner.builder().designerId(Long.valueOf(designerId)).topCategoryId(categoryId.intValue()).sale(sale==1).productCount(count).build());
                    });
                });
            });

            // Merge designer by designerId and majorCategoryId
            Map<String, EsDesigner> summary = new HashMap<>();
            for (EsDesigner designer : designers) {
                String key = designer.getDesignerId() + "-" + designer.getTopCategoryId();

                if (summary.containsKey(key)) {
                    EsDesigner esDesigner = summary.get(key);
                    esDesigner.setProductCount(esDesigner.getProductCount() + designer.getProductCount());
                } else {
                    // TODO Get Designer from somewhere
                    Set<String> suggest = new HashSet<>();
                    if (!isEmpty(designer.getName_en())) {
                        String[] names = designer.getName_en().split(" ");
                        for (String name: names) {
                            suggest.add(name);
                        }
                    }
                    if (!isEmpty(designer.getName_zh())) {
                        suggest.add(designer.getName_zh());
                    }
                    String[] suggests = new String[suggest.size()];

                    designer.setSuggest(new Completion(suggest.toArray(suggests)));
                    summary.put(key, designer);
                }

                if (designer.getSale()) {
                    summary.get(key).setSale(true);
                }
            }

            return summary.values();
        } catch (Exception e) {
            LOGGER.error("Generate designer sales error.", e);
            return designers;
        }
    }

    public String getCategoryTree() {
        ResponseEntity<String> response = magentoClient.exchangeGet(categoryUrl, String.class, null);
        return response.getBody();
    }

    private boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }



    /** ------------- methods down below for mocking data ------------------- */

    public void mockProduct() {
        EsProduct product = EsProduct.builder()
                .id("100")
                .brandId("100")
                .price(10000)
                .brandName("channel")
                .brief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                .name("Black Wool Herris Trousers")
                .photoUrl("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M205048_1.jpg")
                .categories(new Integer[]{100, 1001})
                .category(1001)
                .majorCategory(1)
                .sale(1)
                .designerId(101L)
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
                .photoUrl("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M213021_1.jpg")
                .categories(new Integer[]{101, 1011})
                .category(1011)
                .majorCategory(2)
                .sale(0)
                .designerId(102L)
                .size("36")
                .language("cn")
                .build();

        List<EsProduct> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        productRepository.saveAll(products);
    }

    public void mockDesigner() {
        EsDesigner designer = EsDesigner.builder().id(100L).name_zh("李世民").suggest(new Completion(new String[]{"李世明"})).brief_en("Alumna of prestigious Parisian houses Maison Margiela, Dior, and Balenciaga, Marine Serre debuted her first collection, ‘15-21’, during her fourth year at La Cambre Mode in Brussels. Since, the French designer has established her namesake label as one to watch, garnering the LVMH Prize for Young Fashion Designers in 2017 and launching a menswear line for Spring/Summer 2019. Before pursuing a career in fashion design, Serre initially intended on becoming a professional tennis player. This proclivity for athletics features prominently across Serre’s collections: second-skin tops, leggings, and jet caps, all emblazoned with her instantly-recognizable moon logo, have become Serre’s calling card. Her men’s ready-to-wear offering sees deconstructed chore jackets and parkas referencing the convergence of sportswear and workwear, with all-over logo jeans, bike shorts, and plush jackets summoning 90’s nostalgia. Collaborative sneakers with sportswear giants Nike and Converse round out Serre’s collection of genderless separates.").build();
        EsDesigner designer1 = EsDesigner.builder().id(101L).name_en("Nirvana").suggest(new Completion(new String[]{"Nirvana"})).brief_en("Phillip Lim launched his namesake collection in 2005, naming it “3.1” after his 31 years of age. His first menswear collection debuted shortly after, in 2007. The New York-based designer counts his city as his chief inspiration, but the optimistic, laid-back attitude of Lim’s native California informs his sophisticated yet approachable designs. Fusions of sportswear and tailoring join classic pieces reworked with a minimalist finish. Oversized cuts, graphic colorblocking, and directional prints infuse Lim’s motorcycle and bomber jackets, slim lounge pants, and signature 31 Hour bags with a modern, masculine sleekness.").build();
        EsDesigner designer2 = EsDesigner.builder().id(102L).name_en("Nevermind Never").suggest(new Completion(new String[]{"Nevermind Never"})).brief_en("Based out of New York City, Abasi Rosborough is the namesake menswear brand of Abdul Abasi and Greg Rosborough, who met during their studies at the Fashion Institute of Technology. Following several years spent respectively honing their skills with Ralph Lauren and Engineered Garments, Rosborough and Abasi initiated their collaboration and released their first concept-driven collection in 2013. The pair has since further refined their sartorial output with pieces in simple shapes and progressive cuts that echo a multitude of influences, including sportswear, military uniforms, and classic suiting. Their offering of unadorned but precisely panelled shirts, jackets, and sarouel-style trousers in black and neutral tones fulfills the need for a modular wardrobe of separates that layer seamlessly while making a statement on their own – a pragmatic ideal for the urban sophisticate.").build();
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
