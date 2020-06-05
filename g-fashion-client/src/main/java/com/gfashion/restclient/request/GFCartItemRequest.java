package com.gfashion.restclient.request;

import com.gfashion.domain.cart.GFCartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GFCartItemRequest {
    private GFCartItem cartItem;
}
