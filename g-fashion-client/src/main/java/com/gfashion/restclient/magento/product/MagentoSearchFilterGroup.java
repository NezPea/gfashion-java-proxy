package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoSearchFilterGroup {

    private List<MagentoSearchFilter> filters;
}
