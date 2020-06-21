package com.gfashion.api.mysql;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionProductMysqlResource {


}
