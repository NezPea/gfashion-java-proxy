package com.gfashion.api.dynamodb;

import com.gfashion.api.dynamodb.service.GfContactUsService;
import com.gfashion.data.GfContactUsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/8 21:49
 */
@RestController
@RequestMapping(path = "/gfashion/v1")
public class GfContactUsDynamodbResource {

    @Autowired
    private GfContactUsService gfContactUsService;

    @PutMapping(value = "/dynamodb/contactUs", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfContactUsEntity> insertGfContactUs(@RequestBody GfContactUsEntity contactUs) {

        final GfContactUsEntity gfContactUsEntity = gfContactUsService.saveGfContactUs(contactUs);
        return ResponseEntity.status(HttpStatus.CREATED).body(gfContactUsEntity);
    }

    @GetMapping(value = "/dynamodb/contactUs/{contactUsId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfContactUsEntity> getContactUs(@PathVariable String contactUsId) {

        final GfContactUsEntity contactUs = gfContactUsService.getContactUs(contactUsId);
        return ResponseEntity.status(HttpStatus.OK).body(contactUs);
    }

    @PostMapping(value = "/dynamodb/contactUs", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfContactUsEntity> updateContactUs(@RequestBody GfContactUsEntity contactUs) {

        final GfContactUsEntity gfContactUsEntity = gfContactUsService.updateContactUs(contactUs);
        return ResponseEntity.status(HttpStatus.OK).body(gfContactUsEntity);
    }

}
