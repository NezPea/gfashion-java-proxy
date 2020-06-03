package com.gfashion.api;


import com.gfashion.domain.cart.GfCart;
import com.gfashion.restclient.MagentoCartClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@AllArgsConstructor
public class GfashionCartResource {
    private MagentoCartClient _client;

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<GfCart> getCartById(@PathVariable Integer cartId){
//        return this._client.getCartById(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(this._client.getCartById(cartId));
    }
}
