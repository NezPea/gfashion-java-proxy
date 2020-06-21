package com.gfashion.magento.entity.sales.request;

import com.gfashion.magento.entity.sales.MagentoShipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentReq implements Serializable {

	private MagentoShipment entity;

}
/*
{
    "entity": {
        "order_id": "<integer>",
        "items": [
            {
                "order_item_id": "<integer>",
                "qty": "<number>",
                "additional_data": "<string>",
                "description": "<string>",
                "entity_id": "<integer>",
                "name": "<string>",
                "parent_id": "<integer>",
                "price": "<number>",
                "product_id": "<integer>",
                "row_total": "<number>",
                "sku": "<string>",
                "weight": "<number>",
                "extension_attributes": "<object>"
            },
            {
                "order_item_id": "<integer>",
                "qty": "<number>",
                "additional_data": "<string>",
                "description": "<string>",
                "entity_id": "<integer>",
                "name": "<string>",
                "parent_id": "<integer>",
                "price": "<number>",
                "product_id": "<integer>",
                "row_total": "<number>",
                "sku": "<string>",
                "weight": "<number>",
                "extension_attributes": "<object>"
            }
        ],
        "tracks": [
            {
                "order_id": "<integer>",
                "parent_id": "<integer>",
                "weight": "<number>",
                "qty": "<number>",
                "description": "<string>",
                "track_number": "<string>",
                "title": "<string>",
                "carrier_code": "<string>",
                "created_at": "<string>",
                "entity_id": "<integer>",
                "updated_at": "<string>",
                "extension_attributes": "<object>"
            },
            {
                "order_id": "<integer>",
                "parent_id": "<integer>",
                "weight": "<number>",
                "qty": "<number>",
                "description": "<string>",
                "track_number": "<string>",
                "title": "<string>",
                "carrier_code": "<string>",
                "created_at": "<string>",
                "entity_id": "<integer>",
                "updated_at": "<string>",
                "extension_attributes": "<object>"
            }
        ],
        "comments": [
            {
                "is_customer_notified": "<integer>",
                "parent_id": "<integer>",
                "comment": "<string>",
                "is_visible_on_front": "<integer>",
                "extension_attributes": "<object>",
                "created_at": "<string>",
                "entity_id": "<integer>"
            },
            {
                "is_customer_notified": "<integer>",
                "parent_id": "<integer>",
                "comment": "<string>",
                "is_visible_on_front": "<integer>",
                "extension_attributes": "<object>",
                "created_at": "<string>",
                "entity_id": "<integer>"
            }
        ],
        "billing_address_id": "<integer>",
        "created_at": "<string>",
        "customer_id": "<integer>",
        "email_sent": "<integer>",
        "entity_id": "<integer>",
        "increment_id": "<string>",
        "packages": [
            {
                "extension_attributes": "<object>"
            },
            {
                "extension_attributes": "<object>"
            }
        ],
        "shipment_status": "<integer>",
        "shipping_address_id": "<integer>",
        "shipping_label": "<string>",
        "store_id": "<integer>",
        "total_qty": "<number>",
        "total_weight": "<number>",
        "updated_at": "<string>",
        "extension_attributes": {
            "source_code": "<string>"
        }
    }
}
 */