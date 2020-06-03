package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipmentTrack implements Serializable {
    private String parent_id;
    private String created_at;
    private String updated_at;
    private String carrier_code;
    private String title;
    private String number;
    private String order_id;
    private String track_id;
}
