package com.gfashion.restclient.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartItemRequest<T> {
    private T cartItem;
}
