package com.gfashion.domain.sales.response;

import com.gfashion.domain.sales.GfShipment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GfShipmentResp implements Serializable {
	private List<GfShipment> items;
}
