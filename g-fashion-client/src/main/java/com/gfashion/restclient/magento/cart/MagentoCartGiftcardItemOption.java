package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartGiftcardItemOption {

    @SerializedName("giftcard_amount")
    private String giftcardAmount;
    @SerializedName("custom_giftcard_amount")
    private Integer customGiftcardAmount;
    @SerializedName("giftcard_sender_name")
    private String giftcardSenderName;
    @SerializedName("giftcard_recipient_name")
    private String giftcardRecipientName;
    @SerializedName("giftcard_sender_email")
    private String giftcardSenderEmail;
    @SerializedName("giftcard_recipient_email")
    private String giftcardRecipientEmail;
    @SerializedName("giftcard_message")
    private String giftcardMessage;
    @SerializedName("extension_attributes")
    private MagentoCartGiftcardItemOptionExtensionAttributes extensionAttributes;
}
