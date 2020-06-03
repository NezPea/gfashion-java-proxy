package com.gfashion.restclient.magento.sales;

import com.gfashion.domain.sales.GfShipmentComment;
import com.gfashion.domain.sales.GfShipmentItem;
import com.gfashion.domain.sales.GfShipmentTrack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipment implements Serializable {

	private String increment_id;
	private String store_id;
	private String created_at;
	private String updated_at;
	private String shipping_address_id;
	private String order_id;
	private String total_qty;
	private String shipment_id;

	private List<GfShipmentItem> items;
	private List<GfShipmentTrack> tracks;
	private List<GfShipmentComment> comments;

}
