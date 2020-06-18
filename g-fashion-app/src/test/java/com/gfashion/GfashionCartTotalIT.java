package com.gfashion;

import com.gfashion.domain.cart.*;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link GfCartTotals} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartTotalIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    private List<Integer> cartItemIds;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();

        cartItemIds = new ArrayList<>();

        int cartId = getCartId();
        GfCartItem cartItem = createCartItemParams(cartId, "WT08", 3);
        Integer cartItemId = addCartItem(cartItem);
        cartItemIds.add(cartItemId);

//        cartItem = createCartItemParams(cartId, "product777", 3);
//        cartItemId = addCartItem(cartItem);
//        cartItemIds.add(cartItemId);
    }

    @Test
    public void calculateCartTotal() throws Exception {
        String carrierCode = "flatrate";
        String methodCode = "flatrate";

        GfCartAddress address = createCartAddress();
        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(address))
                .post("/gfashion/v1/carts/estimate-shipping");

        String json = response.getBody().asString();
        Type listType = new TypeToken<List<GfCartEstimateShippingMethod>>() {}.getType();
        List<GfCartEstimateShippingMethod> methods = gson.fromJson(json, listType);
        GfCartEstimateShippingMethod shippingMethod = null;
        for (GfCartEstimateShippingMethod method : methods) {
            if (carrierCode.equals(method.getCarrierCode()) && methodCode.equals(method.getMethodCode())) {
                shippingMethod = method;
                break;
            }
        }

        GfCartAddressInformation params = createShippingInformationParams(carrierCode, methodCode);
        response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information");

        json = response.getBody().asString();
        GfCartShippingInformation shippingInfo = gson.fromJson(json, GfCartShippingInformation.class);
        GfCartTotals totals = shippingInfo.getTotals();

        GfCartTotalItem item1 = totals.getItems().get(0);
//        GfCartTotalItem item2 = totals.getItems().get(1);

        BigDecimal shippingAmount = totals.getShippingAmount();
        BigDecimal baseShippingAmount = totals.getBaseShippingAmount();
        if (shippingMethod != null) {
            assertThat(shippingAmount.compareTo(shippingMethod.getAmount()), is(0));
            assertThat(baseShippingAmount.compareTo(shippingMethod.getBaseAmount()), is(0));
        }

        verifyTotalItem(item1);
//        verifyTotalItem(item2);

        BigDecimal rowTotal1 = item1.getRowTotal();
        BigDecimal discountAmount1 = item1.getDiscountAmount();
//        BigDecimal rowTotal2 = item2.getRowTotal();
//        BigDecimal discountAmount2 = item2.getDiscountAmount();

        BigDecimal subtotal = totals.getSubtotal();
        BigDecimal discountAmount = totals.getDiscountAmount();
        BigDecimal subtotalWithDiscount = totals.getSubtotalWithDiscount();
//        BigDecimal discountResult = discountAmount1.add(discountAmount2).negate();
//        assertThat(subtotal.compareTo(rowTotal1.add(rowTotal2)), is(0));
//        assertThat(discountAmount.compareTo(discountResult), is(0));
        assertThat(subtotalWithDiscount.compareTo(subtotal.add(discountAmount)), is(0));
        assertThat(totals.getGrandTotal().compareTo(subtotalWithDiscount.add(shippingAmount)), is(0));
    }

    private void verifyTotalItem(GfCartTotalItem totalItem) {
        BigDecimal qty = new BigDecimal(totalItem.getQty());
        BigDecimal price = totalItem.getPrice();
        BigDecimal rowTotal = totalItem.getRowTotal();
        assertThat(rowTotal.compareTo(price.multiply(qty)), is(0));

        BigDecimal priceInclTax = totalItem.getPriceInclTax();
        BigDecimal rowTotalInclTax = totalItem.getRowTotalInclTax();
        assertThat(rowTotalInclTax.compareTo(priceInclTax.multiply(qty)), is(0));
    }

    @After
    public void clearTestData() throws Exception {
        for (Integer cartItemId : cartItemIds) {
            if (cartItemId != null) {
                deleteCartItem(cartItemId);
            }
        }
    }
}
