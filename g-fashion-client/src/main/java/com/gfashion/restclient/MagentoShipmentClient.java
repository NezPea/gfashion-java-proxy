package com.gfashion.restclient;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.request.MagentoShipmentReq;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class MagentoShipmentClient {

	@Value("${magento.url.shipment}")
	private String shipmentUrl;

	@Autowired
	private RestClient _restClient;

	private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

	public GfShipment createShipment(GfShipment gfShipment) throws Exception {
		try {
//            Integer totalQty = gfShipment.getItems().stream().mapToInt(GfShipmentItem::getQty).sum();
//            gfShipment.setTotal_qty(totalQty);
			MagentoShipment magentoShipment = _mapper.from(gfShipment);
			MagentoShipmentReq magentoShipmentReq = new MagentoShipmentReq(magentoShipment);
			ResponseEntity<String> responseEntity = this._restClient.postForEntity(shipmentUrl, magentoShipmentReq, String.class, null);
			Gson gson = new Gson();
//            return gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
			return this._mapper.from(gson.fromJson(responseEntity.getBody(), MagentoShipment.class));
		} catch (Exception e) {
			throw e;
		}
    }

    public GfShipment getShipmentById(Integer id) throws Exception {
		String url = shipmentUrl + id;
		try {
			ResponseEntity<String> responseEntity = this._restClient.exchangeGet(url, String.class, null);
			Gson gson = new Gson();
			MagentoShipment magentoShipment = gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
			return this._mapper.from(magentoShipment);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param searchCriteria field1=a or field2=b and field3=c
	 */
	public MagentoShipmentResp queryShipments(String searchCriteria, String fileds) {
		searchCriteria = getSearchCriteria(searchCriteria, fileds);
		log.info(searchCriteria);
		String url = shipmentUrl.substring(0, shipmentUrl.length() - 1) + "s" + searchCriteria;// /shipments?....
		try {
			ResponseEntity<String> responseEntity = this._restClient.exchangeGet(url, String.class, null);
			Gson gson = new Gson();
			MagentoShipmentResp magentoShipmentResp = gson.fromJson(responseEntity.getBody(), MagentoShipmentResp.class);
			return magentoShipmentResp;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String getSearchCriteria(String searchCriteria, String fields) {
		StringBuilder result = new StringBuilder(100);
		result.append("?");
		Pattern p = Pattern.compile("[&|]* *\\w+ *(" + CONDITION + ")+ *[\\w-:,]*");
		Matcher m = p.matcher(searchCriteria);
		String expression;
		String condition, value;
		if (m.find()) {
			expression = m.group().trim();
			Pattern p1 = Pattern.compile("(\\w+) *((" + CONDITION + ")+) *[\\w-:,]*");
			Matcher m1 = p1.matcher(expression);
			while (m1.find()) {
//				System.out.println(m1.group().trim());
//				System.out.println(m1.group(1).trim());
//				System.out.println(m1.group(2).trim());
				condition = m1.group(2).trim();
				result.append("searchCriteria[filter_groups][0][filters][0][field]=").append(m1.group(1).trim()).append("&");
				result.append("searchCriteria[filter_groups][0][filters][0][condition_type]=").append(getCondition(condition)).append("&");
				value = expression.substring(expression.indexOf(condition) + condition.length()).trim();
				if (value.length() > 0) {
					if (value.matches("\\d10T\\d8")) {
						value.replace('T', ' ');
					}
					result.append("searchCriteria[filter_groups][0][filters][0][value]=").append(value).append("&");
				}
			}
		}
		int group = 0, filters = 0;
		while (m.find()) {
			expression = m.group().trim();
			Pattern p1 = Pattern.compile("([&|]*) *(\\w+) *((" + CONDITION + ")+) *[\\w-:,]*");
			Matcher m1 = p1.matcher(expression);
			while (m1.find()) {
//				System.out.println(m1.group().trim());
				System.out.println(m1.group(1).trim());
//				System.out.println(m1.group(2).trim());
//				System.out.println(m1.group(3).trim());
				if (m1.group(1).trim().equals("&")) {
					group++;
					filters = 0;
				} else {
					group = 0;
					filters++;
				}
				condition = m1.group(3).trim();
				result.append("searchCriteria[filter_groups][").append(group).append("][filters][").append(filters).append("][field]=").append(m1.group(2).trim()).append("&");
				result.append("searchCriteria[filter_groups][").append(group).append("][filters][").append(filters).append("][condition_type]=").append(getCondition(condition)).append("&");

				value = expression.substring(expression.indexOf(condition) + condition.length()).trim();
				if (value.length() > 0) {
					if (value.matches("\\d10T\\d8")) {
						value.replace('T', ' ');
					}
					result.append("searchCriteria[filter_groups][").append(group).append("][filters][").append(filters).append("][value]=").append(value).append("&");
				}
			}
		}
		fields = StringUtils.trimToNull(fields);
		if (fields != null) {
			result.append("fields=").append(fields);
		}
		return result.toString();
//		throw new IllegalArgumentException("formatt error:" + searchCriteria);
	}

	public static String CONDITION = "=|!=|>|<|>=|<=|in|like|nin|null|notnull|from|to";

	private static String getCondition(String condition) {
		switch (condition) {
			case "=":
				return "eq";
			case ">":
				return "gt";
			case ">=":
				return "gteq";
			case "<":
				return "lt";
			case "<=":
				return "lteq";
			case "!=":
				return "neq";
			default:
				return condition;
		}
	}

}

