package com.gfashion.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
