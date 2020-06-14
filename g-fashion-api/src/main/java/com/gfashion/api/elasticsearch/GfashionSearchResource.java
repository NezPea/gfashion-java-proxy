package com.gfashion.api.elasticsearch;

import com.gfashion.data.repository.elasticsearch.service.SearchService;
import com.gfashion.domain.elasticsearch.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gfashion/v1")
public class GfashionSearchResource {
    @Resource
    private SearchService searchService;


    @PostMapping("/search")
    public GfProductPage search(@RequestBody GfProductSearchRequest request) {
        return searchService.searchWithCategories(request);
    }

    @PostMapping("/designer")
    public GfDesignerSuggestionResponse designer(@RequestBody GfDesignerSuggestionRequest request) {
        return searchService.designerSuggestion(request.getKeyword());
    }

    @GetMapping("/mock")
    public Map<String, Integer> mock() {
        try {
            searchService.mockProduct();
        } catch (Exception e) {

        }

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

    @GetMapping("/clean")
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
