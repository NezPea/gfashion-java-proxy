package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentComment implements Serializable {

	private String parent_id;
	private String created_at;
	private String updated_at;
	private String comment;
	private String is_customer_notified;
	private String comment_id;

}
