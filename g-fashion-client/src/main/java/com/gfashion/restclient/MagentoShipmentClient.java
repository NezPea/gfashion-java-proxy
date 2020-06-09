package com.gfashion.restclient;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.restclient.magento.exception.ShipmentNotFoundException;
import com.gfashion.restclient.magento.exception.ShipmentUnknowException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.request.MagentoShipmentReq;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

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

	public GfShipment updateShipment(GfShipment gfShipment) throws ShipmentNotFoundException, ShipmentUnknowException {
		try {
//            Integer totalQty = gfShipment.getItems().stream().mapToInt(GfShipmentItem::getQty).sum();
//            gfShipment.setTotal_qty(totalQty);
			MagentoShipment magentoShipment = new MagentoShipment();
			BeanUtils.copyProperties(gfShipment, magentoShipment);
			MagentoShipmentReq magentoShipmentReq = new MagentoShipmentReq(magentoShipment);
			ResponseEntity<String> responseEntity = this._restClient.postForEntity(shipmentUrl, magentoShipmentReq, String.class, null);
			Gson gson = new Gson();
//            return gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
			gfShipment = new GfShipment();
			BeanUtils.copyProperties(gson.fromJson(responseEntity.getBody(), MagentoShipment.class), gfShipment);
			return gfShipment;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new ShipmentNotFoundException(e.getMessage());
			}
			throw new ShipmentUnknowException(e.getMessage());
		}
	}

	public GfShipment getShipmentById(Integer id) throws ShipmentNotFoundException, ShipmentUnknowException {
		String url = shipmentUrl + id;
		try {
			ResponseEntity<String> responseEntity = this._restClient.exchangeGet(url, String.class, null);
			Gson gson = new Gson();
			MagentoShipment magentoShipment = gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
			GfShipment gfShipment = new GfShipment();
			BeanUtils.copyProperties(magentoShipment, gfShipment);
			return gfShipment;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new ShipmentNotFoundException(e.getMessage());
			}
			throw new ShipmentUnknowException(e.getMessage());
		}
	}

	/**
	 * @param searchCriteria 1 Logical OR search: &lt;field&gt;&lt;condition_type&gt;&lt;value&gt;[ | &lt;field&gt;&lt;condition_type&gt;&lt;value&gt;]*<br/>
	 *                       2 Logical AND search: &lt;field&gt;&lt;condition_type&gt;&lt;value&gt;[ & &lt;field&gt;&lt;condition_type&gt;&lt;value&gt;]*<br/>
	 *                       3 Logical AND and OR search: &lt;field&gt;&lt;condition_type&gt;&lt;value&gt; | &lt;field&gt;&lt;condition_type&gt;&lt;value&gt; & &lt;field&gt;&lt;condition_type&gt;&lt;value&gt;<br/>
	 *                       &lt;condition_type&gt;: = == != > < >= <= in like nin null notnull from to<br/>
	 *                       Examples:<br/>
	 *                       field1=a | field2!=b | field3>c<br/>
	 *                       field1=a & field2!=b & field3>c<br/>
	 *                       field1=a | field2!=b | field3==c & field4=2020-06-08T11:20:00 & price from 10 & price to 20<br/>
	 *                       Please use 2020-06-07T20:01:00 for the timestamp.<br/>
	 *                       Be cautious when using too complicated expressions, whether it meets expectations.<br/>
	 * @param fileds         Attributes to return in the response. If you do not specify this parameter, all attributes will be returned.
	 */
	public GfShipmentResp queryShipments(String searchCriteria, String fileds) throws ShipmentNotFoundException, ShipmentUnknowException {
		String url = "shipments" + getSearchCriteria(searchCriteria, fileds);// /shipments?searchCriteria...
		log.info(url);
		try {
			ResponseEntity<String> responseEntity = this._restClient.exchangeGet(url, String.class, null);
			Gson gson = new Gson();
			MagentoShipmentResp magentoShipmentResp = gson.fromJson(responseEntity.getBody(), MagentoShipmentResp.class);
//			return _mapper.from(magentoShipmentResp);
			GfShipmentResp gfShipmentResp = new GfShipmentResp();
			BeanUtils.copyProperties(magentoShipmentResp, gfShipmentResp);
			return gfShipmentResp;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new ShipmentNotFoundException(e.getMessage());
			}
			throw new ShipmentUnknowException(e.getMessage());
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
					if (value.matches("\\d4-\\d2-\\d2T\\d2:\\d2:\\d2")) {
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
//				System.out.println(m1.group(1).trim());
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
					if (value.matches("\\d4-\\d2-\\d2T\\d2:\\d2:\\d2")) {
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
	}

	public static String CONDITION = "=|==|!=|>|<|>=|<=|in|like|nin|null|notnull|from|to";

	private static String getCondition(String condition) {
		switch (condition) {
			case "=":
			case "==":
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

