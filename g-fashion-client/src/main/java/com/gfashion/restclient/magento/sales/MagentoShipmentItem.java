package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentItem implements Serializable {

	private String parent_id;
	private String sku;
	private String name;
	private String order_item_id;
	private String product_id;
	private String weight;
	private String price;
	private String qty;
	private String item_id;

}
