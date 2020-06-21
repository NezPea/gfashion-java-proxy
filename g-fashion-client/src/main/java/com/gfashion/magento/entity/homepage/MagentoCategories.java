package com.gfashion.magento.entity.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MagentoCategories {

    private List<MagentoCategory> items;
}
