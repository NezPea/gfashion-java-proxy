package com.gfashion.api.elasticsearch;

import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import com.gfashion.data.repository.elasticsearch.service.SearchService;
import com.gfashion.domain.elasticsearch.GfDesignerSuggestionRequest;
import com.gfashion.domain.elasticsearch.GfDesignerSuggestionResponse;
import com.gfashion.domain.elasticsearch.GfProductSearchRequest;
import com.gfashion.domain.elasticsearch.GfProductSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/gfashion/v1")
public class GfashionSearchResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GfashionSearchResource.class);

    @Resource
    private SearchService searchService;


    @PostMapping("/search")
    public GfProductSearchResponse search(@RequestBody GfProductSearchRequest request) {
        LOGGER.info("Search product request:{}", request);
        GfProductSearchResponse response = searchService.searchWithCategories(request);
        LOGGER.info("Search product response:{}", response);
        return response;
    }

    @PostMapping("/designer")
    public GfDesignerSuggestionResponse designer(@RequestBody GfDesignerSuggestionRequest request) {
        LOGGER.info("Designer suggestion request:{}", request);
        GfDesignerSuggestionResponse response = searchService.designerSuggestion(request);
        LOGGER.info("Designer suggestion response:{}", response);
        return response;
    }

    @GetMapping("/sale")
    public Collection<EsDesigner> sale() {
        return searchService.generateDesigners();
    }

    @GetMapping("/categories")
    public String categories() {
        return searchService.getCategoryTree();
    }

    @GetMapping("/mock_product")
    public Map<String, Integer> mock() {
        try {
            searchService.mockProduct();
        } catch (Exception e) {

        }

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

    @GetMapping("/clean_product")
    public Map<String, Integer> clean() {
        try {
            searchService.cleanupProducts();
        } catch (Exception e) {

        }

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

    @GetMapping("/mock_designer")
    public Map<String, Integer> mockDesigner() {
        try {
            searchService.mockDesigner();
        } catch (Exception e) {

        }

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

    @GetMapping("/clean_designer")
    public Map<String, Integer> cleanDesigner() {
        try {
            searchService.cleanupDesigners();
        } catch (Exception e) {

        }

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

}
