package com.gfashion.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for G-Fashion designer page
 */

@RestController
@RequestMapping(path = "/gfashion", produces = "application/json")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionDesignerResource {
}
