package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartGiftcardItemOption {

    private String giftcardAmount;
    private Integer customGiftcardAmount;
    private String giftcardSenderName;
    private String giftcardRecipientName;
    private String giftcardSenderEmail;
    private String giftcardRecipientEmail;
    private String giftcardMessage;
    private GfCartGiftcardItemOptionExtensionAttributes extensionAttributes;
}
