package com.gfashion.restclient;

import com.gfashion.domain.cart.GfCart;
import com.gfashion.restclient.magento.cart.MagentoCart;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagentoCartClient {

    @Value("${magento.url.carts}")
    private String cartUrl;

    @Autowired
    private RestClient _restClient;

    private GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfCart getCartById(Integer cartId) {
        String getCartUrl = cartUrl + cartId;

        ResponseEntity<String> responseEntity = this._restClient.exchangeGet(getCartUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoCartToGfCart(gson.fromJson(responseEntity.getBody(), MagentoCart.class));
    }
}
