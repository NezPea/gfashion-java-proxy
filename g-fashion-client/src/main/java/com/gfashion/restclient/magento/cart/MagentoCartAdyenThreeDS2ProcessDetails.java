package com.gfashion.restclient.magento.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAdyenThreeDS2ProcessDetails {

    @SerializedName("threeds2.fingerprint")
    private String threeds2Fingerprint;
    @SerializedName("threeds2.challengeResult")
    private String threeds2ChallengeResult;
}
