package com.gfashion.magento.entity.sales.response;

import com.gfashion.magento.entity.sales.MagentoShipment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MagentoShipmentResp implements Serializable {
	private List<MagentoShipment> items;
}
