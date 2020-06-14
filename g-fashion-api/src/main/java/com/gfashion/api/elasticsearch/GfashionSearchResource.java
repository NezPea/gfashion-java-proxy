package com.gfashion.api.elasticsearch;

import com.gfashion.data.repository.elasticsearch.service.SearchService;
import com.gfashion.domain.elasticsearch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        GfDesignerSuggestionResponse response = searchService.designerSuggestion(request.getKeyword());
        LOGGER.info("Designer suggestion response:{}", response);
        return response;
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
