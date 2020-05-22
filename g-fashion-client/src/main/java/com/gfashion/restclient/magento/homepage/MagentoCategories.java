package com.gfashion.restclient.magento.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MagentoCategories {

    private List<MagentoCategory> items;
}
