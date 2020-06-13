package com.gfashion.api.elasticsearch;

import com.gfashion.data.repository.elasticsearch.service.SearchService;
import com.gfashion.domain.elasticsearch.GfProductPage;
import com.gfashion.domain.elasticsearch.GfProductSearchRequest;
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

    @GetMapping("/mock")
    public Map<String, Integer> mock() {
        searchService.mockData();

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }

    @GetMapping("/clean")
    public Map<String, Integer> clean() {
        searchService.cleanup();

        Map<String, Integer> map = new HashMap<>();
        map.put("status", 0);
        return map;
    }
}
