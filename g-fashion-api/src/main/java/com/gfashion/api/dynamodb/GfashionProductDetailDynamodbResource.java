package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.repository.dynamodb.GfProductDetailRepository;
import com.gfashion.domain.productdetail.ProductDetail;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionProductDetailDynamodbResource {

    private GfProductDetailRepository gfProductDetailRepository;

    @GetMapping(value = "/detail/products", produces = "application/json;charset=utf-8")
    public ResponseEntity<ProductDetail> getProductDetail(@RequestParam(required = false) String productId, @RequestParam(required = false) String locale) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gfProductDetailRepository.getDefaultProductDetailBatchQuery(productId,locale));
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
