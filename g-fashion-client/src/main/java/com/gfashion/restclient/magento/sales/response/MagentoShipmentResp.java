package com.gfashion.restclient.magento.sales.response;

import com.gfashion.restclient.magento.sales.MagentoShipment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MagentoShipmentResp implements Serializable {
	private List<MagentoShipment> items;
}
